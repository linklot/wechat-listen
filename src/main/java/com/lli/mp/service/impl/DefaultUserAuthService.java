package com.lli.mp.service.impl;

import com.lli.mp.entity.User;
import com.lli.mp.service.LocalUserService;
import com.lli.mp.service.UserAuthService;
import com.lli.mp.utils.UserConverter;
import com.lli.mp.wechatclient.client.ClientService;
import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import com.lli.mp.wechatclient.model.UserInfoResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class DefaultUserAuthService implements UserAuthService {
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	private ClientService clientService;
	private UserConverter userConverter;
	private LocalUserService localUserService;

	@Autowired
	public DefaultUserAuthService(ClientService clientService,
	                              UserConverter userConverter,
	                              LocalUserService localUserService) {
		this.clientService = clientService;
		this.userConverter = userConverter;
		this.localUserService = localUserService;
	}

	@Override
	public void userLogin(String code, HttpServletRequest httpRequest) throws Exception {
		AccessTokenResponseModel tokenResponseModel = clientService.getAccessTokenByCode(code);
		UserInfoResponseModel userInfoResponseModel = clientService.getUserInfoByTokenAndOpenId(tokenResponseModel.accessToken, tokenResponseModel.openId);
		User user = userConverter.convertWechatResponseToUser(tokenResponseModel, userInfoResponseModel);
		user = localUserService.saveOrUpdateUser(user);
		httpRequest.getSession().setAttribute("user_id", user.id);

		LOGGER.info("user_id: {}", user.id);
	}
}
