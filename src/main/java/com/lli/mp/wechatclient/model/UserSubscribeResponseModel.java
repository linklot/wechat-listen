package com.lli.mp.wechatclient.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSubscribeResponseModel {
	@JsonProperty("subscribe")
	public int subscribe;

	@JsonProperty("subscribe_time")
	public String subscribeTime;
}
