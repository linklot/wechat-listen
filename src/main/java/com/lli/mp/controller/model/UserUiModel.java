package com.lli.mp.controller.model;

public class UserUiModel {
	private String nickName;
	private String sex;
	private String province;
	private String city;
	private String country;
	private String headImgUrl;

	public UserUiModel(String nickName, String sex, String province,
	                   String city, String country, String headImgUrl) {
		this.nickName = nickName;
		this.sex = sex;
		this.province = province;
		this.city = city;
		this.country = country;
		this.headImgUrl = headImgUrl;
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
}
