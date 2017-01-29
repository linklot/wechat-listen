package com.lli.mp.repository;

import com.lli.mp.entity.MongoUserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoUserDetailsRepository extends MongoRepository<MongoUserDetails, String> {
	MongoUserDetails findByUsername(String username);
}
