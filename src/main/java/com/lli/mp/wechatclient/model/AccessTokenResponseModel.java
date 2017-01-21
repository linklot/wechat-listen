package com.lli.mp.wechatclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokenResponseModel {
    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("expires_in")
    public int expiresIn;

    @JsonProperty("refresh_token")
    public String refreshToken;

    @JsonProperty("openid")
    public String openId;

    @JsonProperty("scope")
    public String scope;

    @JsonProperty("unionid")
    public String unionId;

    @JsonProperty("errcode")
    public String errCode;

    @JsonProperty("errmsg")
    public String errMsg;
}
