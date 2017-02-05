package com.lli.mp.service;

import com.lli.mp.controller.model.UserUiModel;
import com.lli.mp.entity.User;

import java.util.List;

public interface LocalUserService {
	User saveOrUpdateUser(User user);
	User findUserById(String userId);
	List<UserUiModel> findUsers();
	long getUsersCunt();
}
