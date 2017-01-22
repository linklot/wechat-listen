package com.lli.mp.wechatclient.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WechatUriFactoryTest {

    private WechatUriFactory testInstance;

    private String appid = "wx86135fc9a5e67ff3";
    private String wechat_oauth_uri_template = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo#wechat_redirect";
    private String user_details_local_uri="http://whq628318.cn/mp/queryWechatOpenID";

    @Before
    public void initTestCase() {
        testInstance = new WechatUriFactory(appid, wechat_oauth_uri_template, user_details_local_uri);
    }

    @Test
    public void makeWechatOAuthRedirectUri() throws Exception {
        String uri = testInstance.makeWechatOAuthRedirectUri();
        assertEquals("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx86135fc9a5e67ff3&redirect_uri=http%3A%2F%2Fwhq628318.cn%2Fmp%2FqueryWechatOpenID&response_type=code&scope=snsapi_userinfo#wechat_redirect",
                uri);
    }

}