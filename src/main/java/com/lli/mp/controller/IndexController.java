package com.lli.mp.controller;

import com.lli.mp.controller.model.AudioResponseModel;
import com.lli.mp.controller.model.CommentRequestModel;
import com.lli.mp.controller.model.CommentResponseModel;
import com.lli.mp.controller.model.UserUiModel;
import com.lli.mp.entity.User;
import com.lli.mp.service.AudioService;
import com.lli.mp.service.CommentService;
import com.lli.mp.service.LocalUserService;
import com.lli.mp.service.UserAuthService;
import com.lli.mp.wechatclient.util.WechatUriFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping("/mp")
public class IndexController {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private WechatUriFactory wechatUriFactory;
	private UserAuthService userAuthService;
	private AudioService audioService;
	private LocalUserService localUserService;
	private CommentService commentService;

	@Autowired
	public IndexController(WechatUriFactory wechatUriFactory,
	                       UserAuthService userAuthService,
	                       AudioService audioService,
	                       LocalUserService localUserService,
	                       CommentService commentService) {
		this.wechatUriFactory = wechatUriFactory;
		this.userAuthService = userAuthService;
		this.audioService = audioService;
		this.localUserService = localUserService;
		this.commentService = commentService;
	}

	@RequestMapping("/queryWechatAuthCode")
	public String queryWechatAuthCode() {
		String wechatOAuthAuthUri = wechatUriFactory.makeWechatOAuthRedirectUri();
		LOGGER.info("Wechat OAuth uri: {}", wechatOAuthAuthUri);
		return "redirect:"+ wechatOAuthAuthUri;
	}

	@RequestMapping("/queryWechatOpenID")
	public String queryWechatOpenID(@RequestParam(value = "code", defaultValue = "") String code,
	                                @RequestParam(value = "state", defaultValue = "") String state,
	                                HttpServletRequest httpRequest, Model model) {
		if(StringUtils.isEmpty(code)) {
			return "errorPage";
		}

		try {
			userAuthService.userLogin(code, httpRequest);
		} catch (Exception e) {
			LOGGER.error("error: {}", e.getMessage());
		}

		model.addAttribute("name", "Bob Smith");

		return "redirect:index";
	}

	@RequestMapping("/index")
	public String indexPage(HttpServletRequest httpRequest, Model model) {
		boolean userSignedIn = userAuthService.isUserSignedIn(httpRequest);

		if(!userSignedIn) {
			return "redirect:queryWechatAuthCode";
		}

		UserUiModel userUiModel = userAuthService.getCurrentUser(httpRequest);
		model.addAttribute("user", userUiModel);

		List<AudioResponseModel> audioModels = audioService.getAudiosForUI();
		model.addAttribute("audios", audioModels);

		return "index";
	}

	@RequestMapping("/mediaDetail")
	public String mediaPage(@RequestParam("id") String id,
	                        HttpServletRequest httpRequest, Model model) {
		model.addAttribute("id", id);

		boolean userSignedIn = userAuthService.isUserSignedIn(httpRequest);

		if(!userSignedIn) {
			return "redirect:queryWechatAuthCode";
		}

		try {
			boolean userSubscribed = userAuthService.isUserSubscribed(httpRequest);
			model.addAttribute("subscribed", userSubscribed);
		} catch (Exception e) {
			model.addAttribute("subscribed", false);
		}

		UserUiModel userUiModel = userAuthService.getCurrentUser(httpRequest);
		model.addAttribute("user", userUiModel);

		AudioResponseModel audioModel = audioService.getAudioForUI(id);
		model.addAttribute("audio", audioModel);

		return "mediaDetail";
	}

	@RequestMapping("/coverImg/{audioId}")
	public void sendCoverImg(@PathVariable("audioId") String audioId, HttpServletResponse response) throws IOException {
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		Path imgPath = audioService.getCoverImgPath(audioId);
		Files.copy(imgPath, response.getOutputStream());
	}

	@RequestMapping("/audioPreview/{audioId}")
	public String audioPreview(@PathVariable("audioId") String audioId, Model model) {
		model.addAttribute("audio", audioService.getAudioForUI(audioId));
		return "audioPreview";
	}

	@RequestMapping("/audioPlayTimes/{audioId}")
	@ResponseStatus(value = HttpStatus.OK)
	public void increaseAudioPlayTimes(@PathVariable("audioId") String audioId) {
		audioService.increaseAudioPlayTimes(audioId);
	}

	@RequestMapping(value = "/audioComment", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void addComment(HttpServletRequest request, @RequestBody CommentRequestModel commentModel) {
		HttpSession session = request.getSession();
		String userId = session.getAttribute("user_id") == null ? "5885de46f4a3dc1df8c1cc4b" : session.getAttribute("user_id").toString();
		User currentUser = localUserService.findUserById(userId);
		commentService.createComment(userId, currentUser.nickName, commentModel);
	}

	@RequestMapping(value = "/audio/{audioId}/comments", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<CommentResponseModel> getComments(@PathVariable("audioId") String audioId) {
		List<CommentResponseModel> comments = commentService.findComments(audioId);
		return comments;
	}

	@RequestMapping("/local_mediaDetail")
	public String localMediaPage(@RequestParam("id") String id,
	                        HttpServletRequest httpRequest, Model model) {
		model.addAttribute("id", id);

		UserUiModel userUiModel = new UserUiModel("一二三", "1", "province", "city", "country", "/image/avatar.png");
		model.addAttribute("user", userUiModel);

		boolean isSubscribed = false;
		model.addAttribute("subscribed", isSubscribed);

		AudioResponseModel audioModel = audioService.getAudioForUI(id);
		model.addAttribute("audio", audioModel);

		return "mediaDetail";
	}

	@RequestMapping("/local_index")
	public String localIndex(Model model) {
		UserUiModel userUiModel = new UserUiModel("一二三", "1", "province", "city", "country", "/image/avatar.png");
		model.addAttribute("user", userUiModel);

		List<AudioResponseModel> audioModels = audioService.getAudiosForUI();
		model.addAttribute("audios", audioModels);

		return "index";
	}
}
