package com.lli.mp.service.impl;

import com.lli.mp.controller.model.UserUiModel;
import com.lli.mp.entity.CoreAccessToken;
import com.lli.mp.entity.User;
import com.lli.mp.service.CoreAccessTokenService;
import com.lli.mp.service.LocalUserService;
import com.lli.mp.service.UserAuthService;
import com.lli.mp.utils.UserConverter;
import com.lli.mp.wechatclient.client.ClientService;
import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import com.lli.mp.wechatclient.model.CoreAccessTokenResponseModel;
import com.lli.mp.wechatclient.model.UserInfoResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class DefaultUserAuthService implements UserAuthService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private ClientService clientService;
	private UserConverter userConverter;
	private LocalUserService localUserService;
	private CoreAccessTokenService coreAccessTokenService;

	@Autowired
	public DefaultUserAuthService(ClientService clientService,
	                              UserConverter userConverter,
	                              LocalUserService localUserService,
	                              CoreAccessTokenService coreAccessTokenService) {
		this.clientService = clientService;
		this.userConverter = userConverter;
		this.localUserService = localUserService;
		this.coreAccessTokenService = coreAccessTokenService;
	}

	@Override
	public void userLogin(String code, HttpServletRequest httpRequest) throws Exception {
		AccessTokenResponseModel tokenResponseModel = clientService.getAccessTokenByCode(code);
		UserInfoResponseModel userInfoResponseModel = clientService.getUserInfoByTokenAndOpenId(tokenResponseModel.accessToken, tokenResponseModel.openId);
		User user = userConverter.convertWechatResponseToUser(tokenResponseModel, userInfoResponseModel);
		user = localUserService.saveOrUpdateUser(user);
		httpRequest.getSession().setAttribute("user_id", user.id);
		LOGGER.info("User {} sign-in. Openid: {}", user.id, user.openId);
	}

	@Override
	public boolean isUserSignedIn(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession();
		if(session != null && session.getAttribute("user_id") != null) {
			return isNotBlank(session.getAttribute("user_id").toString());
		}
		return false;
	}

	@Override
	public String getUserIdFromSession(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession();
		if(session == null || session.getAttribute("user_id") == null) {
			return "";
		}
		return session.getAttribute("user_id").toString();
	}

	@Override
	public UserUiModel getCurrentUser(HttpServletRequest httpRequest) {
		String userId = getUserIdFromSession(httpRequest);
		User user = getUserById(userId);
		return new UserUiModel(
				user.nickName, user.sex, user.province, user.city,
				user.country, user.headImgUrl
		);
	}

	@Override
	public boolean isUserSubscribed(HttpServletRequest httpRequest) throws Exception {
		String userId = getUserIdFromSession(httpRequest);
		CoreAccessToken coreAccessToken = getCoreAccessToken();
		User user = getUserById(userId);
		String openId = user.openId;

		try {
			LOGGER.info("1st try. accessToken: {}, openId: {}", coreAccessToken.accessToken, openId);
			return clientService.isUserSubscribed(coreAccessToken.accessToken, openId);
		} catch (Exception e) {
			coreAccessToken = refreshCoreAccessToken();
			try {
				LOGGER.info("2nd try. accessToken: {}, openId: {}", coreAccessToken.accessToken, openId);
				return clientService.isUserSubscribed(coreAccessToken.accessToken, openId);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	private User getUserById(String userId) {
		return localUserService.findUserById(userId);
	}

	private CoreAccessToken getCoreAccessToken() throws Exception {
		CoreAccessToken token = coreAccessTokenService.getCoreAccessToken();
		return token == null ? refreshCoreAccessToken() : token;
	}

	private CoreAccessToken refreshCoreAccessToken() throws Exception {
		CoreAccessTokenResponseModel responseModel = clientService.getCoreAccesstokenModel();
		return coreAccessTokenService.updateCoreAccessToken(responseModel);
	}
}
