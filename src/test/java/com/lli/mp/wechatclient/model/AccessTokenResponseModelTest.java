package com.lli.mp.wechatclient.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class AccessTokenResponseModelTest {

    private AccessTokenResponseModel model;

    @Test
    public void testDeserialization() throws IOException {
        String json = givenJson();
        ObjectMapper mapper = new ObjectMapper();
        model = mapper.readValue(json, AccessTokenResponseModel.class);

        assertEquals("ACCESS_TOKEN", model.accessToken);
        assertEquals(7200, model.expiresIn);
        assertEquals("REFRESH_TOKEN", model.refreshToken);
        assertEquals("OPENID", model.openId);
        assertEquals("SCOPE", model.scope);
        assertEquals("o6_bmasdasdsad6_2sgVt7hMZOPfL", model.unionId);
        assertTrue(StringUtils.isEmpty(model.errCode));
        assertTrue(StringUtils.isEmpty(model.errMsg));
    }

    @Test
    public void testEmptyDeserialization() throws IOException {
        String json = givenEmptyJson();
        ObjectMapper mapper = new ObjectMapper();
        model = mapper.readValue(json, AccessTokenResponseModel.class);

        assertNotNull(model);
        assertTrue(StringUtils.isNotEmpty(model.errCode));
        assertTrue(StringUtils.isNotEmpty(model.errMsg));
    }

    private String givenJson() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append("    \"access_token\": \"ACCESS_TOKEN\",");
        sb.append("    \"expires_in\": 7200,");
        sb.append("    \"refresh_token\": \"REFRESH_TOKEN\",");
        sb.append("    \"openid\": \"OPENID\",");
        sb.append("    \"scope\": \"SCOPE\",");
        sb.append("    \"unionid\": \"o6_bmasdasdsad6_2sgVt7hMZOPfL\"");
        sb.append("}");

        return sb.toString();
    }

    private String givenEmptyJson() {
        StringBuilder sb = new StringBuilder();

        sb.append("{");
        sb.append("    \"errcode\": \"err code\",");
        sb.append("    \"errmsg\": \"err message\"");
        sb.append("}");
        return sb.toString();
    }

}