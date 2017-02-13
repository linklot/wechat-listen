package com.lli.mp.wechatclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoreAccessTokenResponseModel {
	@JsonProperty("access_token")
	public String accessToken;

	@JsonProperty("expires_in")
	public int expiresIn;
}
