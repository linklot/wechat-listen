package com.lli.mp.controller.model;

import com.lli.mp.utils.DateTimeUtils;

public class UserUiModel {
	public String userId;
	public String nickName;
	public String sex;
	public String province;
	public String city;
	public String country;
	public String headImgUrl;
	public String lastLoginDateTime;

	public UserUiModel(String nickName, String sex, String province,
	                   String city, String country, String headImgUrl) {
		this.nickName = nickName;
		this.sex = sex;
		this.province = province;
		this.city = city;
		this.country = country;
		this.headImgUrl = headImgUrl;
		lastLoginDateTime = "";
	}

	public String getNickName() {
		return nickName;
	}

	public String getSex() {
		return sex;
	}


	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public String getLastLoginDateTime() {
		return lastLoginDateTime;
	}

	public String getFriendlyLastLoginDateTime() {
		try {
			return DateTimeUtils.toCommentFriendlyString(lastLoginDateTime);
		} catch (Exception e) {}

		return "";
	}
}
