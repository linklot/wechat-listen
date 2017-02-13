package com.lli.mp.repository;

import com.lli.mp.entity.CoreAccessToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CoreAccessTokenRepository extends MongoRepository<CoreAccessToken, String> {
}
