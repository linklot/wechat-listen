package com.lli.mp.entity;

import org.springframework.data.annotation.Id;

public class CoreAccessToken {
	@Id
	public String id;

	public String accessToken;
	public int expiredIn;
}
