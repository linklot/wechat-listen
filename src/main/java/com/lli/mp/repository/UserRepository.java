package com.lli.mp.repository;

import com.lli.mp.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
	User findByOpenId(String openId);
}
