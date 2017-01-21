package com.lli.mp.wechatclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoResponseModel {
    @JsonProperty("openid")
    public String openId;

    @JsonProperty("nickname")
    public String nickName;

    @JsonProperty("sex")
    public String sex;

    @JsonProperty("province")
    public String province;

    @JsonProperty("city")
    public String city;

    @JsonProperty("country")
    public String country;

    @JsonProperty("headimgurl")
    public String headImgUrl;

    @JsonProperty("unionid")
    public String unionId;
}
