package com.lli.mp.wechatclient.client;

import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import com.lli.mp.wechatclient.model.UserInfoResponseModel;

public interface ClientService {
    AccessTokenResponseModel getAccessTokenByCode(String code);
    UserInfoResponseModel getUserInfoByTokenAndOpenId(String accessToken, String openId);
}
