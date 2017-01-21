package com.lli.mp.wechatclient.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DefaultRestTemplate.class)
public class DefaultRestTemplateTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void testNormal() {
        String result = restTemplate.getForObject("https://www.google.com.au/?gfe_rd=cr&ei=yWCEWM-tIcXN8ge1zrCQAQ", String.class);
        assertTrue(result.length() > 1);
    }
}