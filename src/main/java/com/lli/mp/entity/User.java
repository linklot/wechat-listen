package com.lli.mp.entity;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class User {

	@Id
	public String id;

	public String openId;
	public String nickName;
	public String sex;
	public String province;
	public String city;
	public String country;
	public String headImgUrl;
	public String unionId;
	public String accessToken;
	public Integer expiresIn;
	public String refreshToken;
	public String scope;
	public String lastLoginDateTime;

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", openId='" + openId + '\'' +
				", nickName='" + nickName + '\'' +
				", sex='" + sex + '\'' +
				", province='" + province + '\'' +
				", city='" + city + '\'' +
				", country='" + country + '\'' +
				", headImgUrl='" + headImgUrl + '\'' +
				", unionId='" + unionId + '\'' +
				", accessToken='" + accessToken + '\'' +
				", expiresIn=" + expiresIn +
				", refreshToken='" + refreshToken + '\'' +
				", scope='" + scope + '\'' +
				", lastLoginDateTime='" + lastLoginDateTime + '\'' +
				'}';
	}
}
