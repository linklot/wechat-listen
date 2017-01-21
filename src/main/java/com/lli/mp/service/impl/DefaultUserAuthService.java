package com.lli.mp.service.impl;

import com.lli.mp.service.UserAuthService;
import com.lli.mp.wechatclient.client.ClientService;
import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import com.lli.mp.wechatclient.model.UserInfoResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class DefaultUserAuthService implements UserAuthService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private ClientService clientService;

    @Autowired
    public DefaultUserAuthService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public UserInfoResponseModel getUserDetailsPostAuth(String code, HttpServletResponse httpResponse) {
        AccessTokenResponseModel tokenResponseModel = clientService.getAccessTokenByCode(code);
        return clientService.getUserInfoByTokenAndOpenId(tokenResponseModel.accessToken, tokenResponseModel.openId);
    }
}
