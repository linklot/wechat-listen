package com.lli.mp.service.impl;

import com.lli.mp.entity.CoreAccessToken;
import com.lli.mp.repository.CoreAccessTokenRepository;
import com.lli.mp.service.CoreAccessTokenService;
import com.lli.mp.wechatclient.model.CoreAccessTokenResponseModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultCoreAccessTokenService implements CoreAccessTokenService {
	private CoreAccessTokenRepository repository;

	public DefaultCoreAccessTokenService(CoreAccessTokenRepository repository) {
		this.repository = repository;
	}

	@Override
	public CoreAccessToken getCoreAccessToken() {
		List<CoreAccessToken> tokens = repository.findAll();
		if(tokens != null && tokens.size() > 0) {
			return tokens.get(0);
		}
		return null;
	}

	@Override
	public CoreAccessToken updateCoreAccessToken(CoreAccessTokenResponseModel model) {
		List<CoreAccessToken> tokens = repository.findAll();
		if(tokens == null || tokens.size() <= 0) {
			// Create a new entity
			CoreAccessToken token = new CoreAccessToken();
			token.accessToken = model.accessToken;
			token.expiredIn = model.expiresIn;
			return repository.save(token);
		} else {
			// Update the entity
			CoreAccessToken token = tokens.get(0);
			token.accessToken = model.accessToken;
			token.expiredIn = model.expiresIn;
			return repository.save(token);
		}
	}
}
