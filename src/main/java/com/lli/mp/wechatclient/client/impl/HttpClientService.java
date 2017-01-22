package com.lli.mp.wechatclient.client.impl;

import com.lli.mp.wechatclient.client.ClientService;
import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import com.lli.mp.wechatclient.model.UserInfoResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpClientService implements ClientService {

	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private String appId;
    private String appsecret;
    private String wechatAccessTokenUriTemplate;
    private String wechatUserInfoUriTemplate;
    private RestTemplate restTemplate;

    @Autowired
    public HttpClientService(@Value("${appid}") String appId,
                             @Value("${appsecret}") String appsecret,
                             @Value("${wechat_accesstoken_uri_template}") String wechatAccessTokenUriTemplate,
                             @Value("${wechat_userinfo_uri_template}") String wechatUserInfoUriTemplate,
                             RestTemplate restTemplate) {
        this.appId = appId;
        this.appsecret = appsecret;
        this.wechatAccessTokenUriTemplate = wechatAccessTokenUriTemplate;
        this.wechatUserInfoUriTemplate = wechatUserInfoUriTemplate;
        this.restTemplate = restTemplate;
    }

    @Override
    public AccessTokenResponseModel getAccessTokenByCode(String code) {
        String wechatAccessTokenUri = String.format(wechatAccessTokenUriTemplate, appId, appsecret, code);
        return restTemplate.getForObject(wechatAccessTokenUri, AccessTokenResponseModel.class);
    }

    @Override
    public UserInfoResponseModel getUserInfoByTokenAndOpenId(String accessToken, String openId) {
        System.out.println(wechatUserInfoUriTemplate);
        String wechatUserInfoUri = String.format(wechatUserInfoUriTemplate, accessToken, openId);
        return restTemplate.getForObject(wechatUserInfoUri, UserInfoResponseModel.class);
    }
}
