package com.lli.mp.service;

import com.lli.mp.wechatclient.model.UserInfoResponseModel;

import javax.servlet.http.HttpServletResponse;

public interface UserAuthService {
    UserInfoResponseModel getUserDetailsPostAuth(String code, HttpServletResponse httpResponse) throws Exception;
}
