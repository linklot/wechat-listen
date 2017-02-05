package com.lli.mp.service.impl;

import com.lli.mp.controller.model.UserUiModel;
import com.lli.mp.entity.User;
import com.lli.mp.repository.UserRepository;
import com.lli.mp.service.LocalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DefaultLocalUserService implements LocalUserService {

	private UserRepository userRepository;

	@Autowired
	public DefaultLocalUserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User saveOrUpdateUser(User user) {
		User eUser = userRepository.findByOpenId(user.openId);

		if(eUser != null) {
			eUser = userRepository.save(populateExistingUser(eUser, user));
		} else {
			eUser = userRepository.save(user);
		}

		return eUser;
	}

	@Override
	public User findUserById(String userId) {
		return userRepository.findOne(userId);
	}

	@Override
	public Page<UserUiModel> findUsers(int pageNumber, int pageSize) {
		Sort sort = new Sort(Sort.Direction.ASC, "nickName");
		Pageable pageable = new PageRequest(pageNumber, pageSize, Sort.Direction.ASC, "nickName");
		Page<User> users = userRepository.findAll(pageable);
		return users.map(user -> {
			UserUiModel userModel = new UserUiModel(user.nickName, user.sex, user.province, user.city, user.country, user.headImgUrl);
			userModel.lastLoginDateTime = user.lastLoginDateTime;
			return userModel;
		});
	}

	@Override
	public long getUsersCunt() {
		return userRepository.count();
	}

	private User populateExistingUser(User eUser, User user) {
		eUser.nickName = user.nickName;
		eUser.sex = user.sex;
		eUser.province = user.province;
		eUser.city = user.city;
		eUser.country = user.country;
		eUser.headImgUrl = user.headImgUrl;
		eUser.unionId = user.unionId;
		eUser.accessToken = user.accessToken;
		eUser.expiresIn = user.expiresIn;
		eUser.refreshToken = user.refreshToken;
		eUser.scope = user.scope;
		eUser.lastLoginDateTime = user.lastLoginDateTime;
		return eUser;
	}
}
