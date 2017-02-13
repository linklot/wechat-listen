package com.lli.mp.wechatclient.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lli.mp.wechatclient.client.ClientService;
import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import com.lli.mp.wechatclient.model.UserInfoResponseModel;
import com.lli.mp.wechatclient.model.UserSubscribeResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Service
public class HttpClientService implements ClientService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private String appId;
    private String appsecret;
    private String wechatAccessTokenUriTemplate;
    private String wechatUserInfoUriTemplate;
    private String wechatUserSubscribeTemplate;
    private RestTemplate restTemplate;

    @Autowired
    public HttpClientService(@Value("${appid}") String appId,
                             @Value("${appsecret}") String appsecret,
                             @Value("${wechat_accesstoken_uri_template}") String wechatAccessTokenUriTemplate,
                             @Value("${wechat_userinfo_uri_template}") String wechatUserInfoUriTemplate,
                             @Value("${wechat_user_subscribe_template}") String wechatUserSubscribeTemplate,
                             RestTemplate restTemplate) {
        this.appId = appId;
        this.appsecret = appsecret;
        this.wechatAccessTokenUriTemplate = wechatAccessTokenUriTemplate;
        this.wechatUserInfoUriTemplate = wechatUserInfoUriTemplate;
        this.wechatUserSubscribeTemplate = wechatUserSubscribeTemplate;
        this.restTemplate = restTemplate;
        this.restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        this.restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    }

    @Override
    public AccessTokenResponseModel getAccessTokenByCode(String code) throws Exception {
        String wechatAccessTokenUri = String.format(wechatAccessTokenUriTemplate, appId, appsecret, code);
        String response = restTemplate.getForObject(wechatAccessTokenUri, String.class);

	    ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, AccessTokenResponseModel.class);
    }

    @Override
    public UserInfoResponseModel getUserInfoByTokenAndOpenId(String accessToken, String openId) throws Exception {
        String wechatUserInfoUri = String.format(wechatUserInfoUriTemplate, accessToken, openId);
        String response = restTemplate.getForObject(wechatUserInfoUri, String.class);

        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, UserInfoResponseModel.class);
    }

	@Override
	public boolean isUserSubscribed(String accessToken, String openId) throws Exception {
    	String userSubscribeUri = String.format(wechatUserSubscribeTemplate, accessToken, openId);
    	LOGGER.info("Get user subscribe info: {}", userSubscribeUri);
    	String response = restTemplate.getForObject(userSubscribeUri, String.class);

    	ObjectMapper mapper = new ObjectMapper();
		UserSubscribeResponseModel responseModel = mapper.readValue(response, UserSubscribeResponseModel.class);
		return responseModel.subscribe == 1;
	}
}
