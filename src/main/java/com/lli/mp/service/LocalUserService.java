package com.lli.mp.service;

import com.lli.mp.entity.User;

public interface LocalUserService {
	User saveOrUpdateUser(User user);
	User findUserById(String userId);
}
