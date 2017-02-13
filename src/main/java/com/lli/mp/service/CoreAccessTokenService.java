package com.lli.mp.service;

import com.lli.mp.entity.CoreAccessToken;
import com.lli.mp.wechatclient.model.CoreAccessTokenResponseModel;

public interface CoreAccessTokenService {
	CoreAccessToken getCoreAccessToken();
	CoreAccessToken updateCoreAccessToken(CoreAccessTokenResponseModel model);
}
