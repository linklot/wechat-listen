package com.lli.mp.service;

import com.lli.mp.controller.model.UserUiModel;
import com.lli.mp.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LocalUserService {
	User saveOrUpdateUser(User user);
	User findUserById(String userId);
	Page<UserUiModel> findUsers(int pageNumber, int pageSize);
	long getUsersCunt();
}
