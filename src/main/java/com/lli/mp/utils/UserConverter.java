package com.lli.mp.utils;

import com.lli.mp.entity.User;
import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import com.lli.mp.wechatclient.model.UserInfoResponseModel;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

	public User convertWechatResponseToUser(AccessTokenResponseModel accessTokenResponse,
	                                        UserInfoResponseModel userInfoResponse) {
		User user = new User();

		user.openId = accessTokenResponse.openId;
		user.nickName = userInfoResponse.nickName;
		user.sex = userInfoResponse.sex;
		user.province = userInfoResponse.province;
		user.city = userInfoResponse.city;
		user.country = userInfoResponse.country;
		user.headImgUrl = userInfoResponse.headImgUrl;
		user.unionId = accessTokenResponse.unionId;
		user.accessToken = accessTokenResponse.accessToken;
		user.expiresIn = accessTokenResponse.expiresIn;
		user.refreshToken = accessTokenResponse.refreshToken;
		user.scope = accessTokenResponse.scope;
		user.lastLoginDateTime = new DateTime().toString(ISODateTimeFormat.dateTime());

		return user;
	}
}
