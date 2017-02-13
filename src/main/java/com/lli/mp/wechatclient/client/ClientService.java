package com.lli.mp.wechatclient.client;

import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import com.lli.mp.wechatclient.model.CoreAccessTokenResponseModel;
import com.lli.mp.wechatclient.model.UserInfoResponseModel;

public interface ClientService {
    AccessTokenResponseModel getAccessTokenByCode(String code) throws Exception;
    UserInfoResponseModel getUserInfoByTokenAndOpenId(String accessToken, String openId) throws Exception;

    CoreAccessTokenResponseModel getCoreAccesstokenModel() throws Exception;
    boolean isUserSubscribed(String accessToken, String openId) throws Exception;
}
