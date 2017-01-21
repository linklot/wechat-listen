package com.lli.mp.service.impl;

import com.lli.mp.wechatclient.client.ClientService;
import com.lli.mp.wechatclient.model.AccessTokenResponseModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletResponse;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class DefaultUserAuthServiceTest {

	private DefaultUserAuthService testInstance;

	private ClientService clientService;
	private HttpServletResponse httpServletResponse;

	@Before
	public void initTestCase() {
		clientService = mock(ClientService.class);
		httpServletResponse = mock(HttpServletResponse.class);
		testInstance = new DefaultUserAuthService(clientService);
	}

	@Test
	public void getUserDetailsPostAuth() throws Exception {
		when(clientService.getAccessTokenByCode("the_code")).thenReturn(givenAccessTokenResponseModel());
		testInstance.getUserDetailsPostAuth("the_code", httpServletResponse);
		verify(clientService, times(1)).getAccessTokenByCode("the_code");
		verify(clientService, times(1)).getUserInfoByTokenAndOpenId(anyString(), anyString());
	}

	private AccessTokenResponseModel givenAccessTokenResponseModel() {
		AccessTokenResponseModel model = new AccessTokenResponseModel();
		model.accessToken = "access_token";
		model.openId = "open_id";
		return model;
	}

}