package com.lli.mp.wechatclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class WechatUriFactory {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private String appid;
    private String wechatOAuthUriTemplate;
    private String userDetailsLocalUri;

    public WechatUriFactory(@Value("${appid}") String appid,
                            @Value("${wechat_oauth_uri_template}") String wechatOAuthUriTemplate,
                            @Value("${user_details_local_uri}") String userDetailsLocalUri) {
        this.appid = appid;
        this.wechatOAuthUriTemplate = wechatOAuthUriTemplate;
        this.userDetailsLocalUri = userDetailsLocalUri;
    }

    public String makeWechatOAuthRedirectUri() {
        try {
            String wechatOAuthUri = String.format(wechatOAuthUriTemplate, appid,
                    URLEncoder.encode(userDetailsLocalUri, StandardCharsets.UTF_8.toString()));
            return wechatOAuthUri;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Unsupported encoding!");
        }

        return "";
    }
}
