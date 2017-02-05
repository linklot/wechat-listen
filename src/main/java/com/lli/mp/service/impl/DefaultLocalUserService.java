package com.lli.mp.service.impl;

import com.lli.mp.controller.model.UserUiModel;
import com.lli.mp.entity.User;
import com.lli.mp.repository.UserRepository;
import com.lli.mp.service.LocalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
	public List<UserUiModel> findUsers() {
		Sort sort = new Sort(Sort.Direction.ASC, "nickName");
		List<User> users = userRepository.findAll(sort);
		return users.stream().map(user -> {
			UserUiModel userModel = new UserUiModel(user.nickName, user.sex, user.province, user.city, user.country, user.headImgUrl);
			userModel.lastLoginDateTime = user.lastLoginDateTime;
			return userModel;
		}).collect(Collectors.toList());
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
