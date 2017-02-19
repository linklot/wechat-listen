package com.lli.mp.controller;

import com.lli.mp.controller.model.AudioResponseModel;
import com.lli.mp.controller.model.CommentResponseModel;
import com.lli.mp.controller.model.ShowHideCommentsRequestModel;
import com.lli.mp.service.AudioService;
import com.lli.mp.service.CommentService;
import com.lli.mp.service.LocalUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final int PAGE_SIZE = 20;

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private AudioService audioService;
	private LocalUserService localUserService;
	private CommentService commentService;

	@Autowired
	public AdminController(AudioService audioService, LocalUserService localUserService,
	                       CommentService commentService) {
		this.audioService = audioService;
		this.localUserService = localUserService;
		this.commentService = commentService;
	}

	@RequestMapping("")
	public String adminIndex(Model model) {
		model.addAttribute("audios", audioService.getAudiosForUI());
		return "adminAudios";
	}

	@RequestMapping("/createAudio")
	public String addAudio() {
		return "createAudio";
	}

	@RequestMapping(value = "/createAudio", method = RequestMethod.POST)
	public String createAudio(
			@RequestParam("title") String title,
			@RequestParam("audioFile") MultipartFile audioFile,
			@RequestParam("description") String description,
			@RequestParam("coverImg") MultipartFile coverImg) {

		if (isNotEmpty(title) && isNotEmpty(description) && audioFile.getSize() > 0 && coverImg.getSize() > 0) {
			try {
				audioService.saveAudio(title, description, coverImg, audioFile);
			} catch (IOException e) {
				LOGGER.error("Cannot upload audio. reason: {}", e.getMessage());
			}
		}
		return "redirect:/admin";
	}

	@RequestMapping("/audios/audio/{audioId}")
	public String editAudio(@PathVariable String audioId, Model model) {
		AudioResponseModel audioModel = audioService.getAudioForUI(audioId);
		model.addAttribute("audio", audioModel);
		return "adminUpdateAudio";
	}

	@RequestMapping(value = "/audios/audio", method = RequestMethod.POST)
	public String submitUpdatedAudio(
			@RequestParam("audioId") String audioId,
			@RequestParam("title") String title,
			@RequestParam("audioFile") MultipartFile audioFile,
			@RequestParam("description") String description,
			@RequestParam("coverImg") MultipartFile coverImg) {
		try {
			audioService.updateAudio(audioId, title, description, audioFile, coverImg);
		} catch (IOException e) {
			LOGGER.error("Failed to update audio. Id: {}", audioId);
		}
		return "redirect:/admin";
	}

	@RequestMapping(value = "/delAudios", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
	@ResponseStatus(value = HttpStatus.OK)
	public void deleteAudios(@RequestBody List<String> audioIds) {
		audioService.deleteAudios(audioIds);
	}

	@RequestMapping(value = "/users/{pageNumber}", method = RequestMethod.GET)
	public String findUsers(@PathVariable("pageNumber") Integer pageNumber, Model model) {
		int currentPage = pageNumber == null ? 0 : pageNumber.intValue();
		model.addAttribute("count", localUserService.getUsersCunt());
		model.addAttribute("users", localUserService.findUsers(currentPage, PAGE_SIZE));
		return "adminUsers";
	}

	@RequestMapping(value = "/audios/audio/{audioId}/comments", produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<CommentResponseModel> getAudioComments(@PathVariable("audioId") String audioId) {
		return commentService.findComments(audioId);
	}

	@RequestMapping(value = "/comments", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8", produces = "application/json; charset=UTF-8")
	@ResponseStatus(value = HttpStatus.OK)
	public void hideShowComments(@RequestBody ShowHideCommentsRequestModel showHideCommentsRequestModel) {
		commentService.updateShowHide(showHideCommentsRequestModel.audioId, showHideCommentsRequestModel.idsToHide);
	}
}
