package com.bro.blog.service;


import com.bro.blog.base.bean.User;
import com.bro.blog.base.request.UserRequest;
import com.bro.blog.dao.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@Service
public class UserService {
	private Logger logger = LoggerFactory.getLogger(UserService.class);


	@Resource
	UserMapper userMapper;


	public int reg(UserRequest userRequest) {
		if (userMapper.checkUsernameExist(userRequest.getUsername()) != 0) {
			return 1;
		}
		if (userMapper.checkEmailExist(userRequest.getEmail()) != 0) {
			return 2;
		}
		//插入用户,插入之前先对密码进行MD5
		User user = new User();
		user.setUsername(userRequest.getUsername());
		user.setPassword(DigestUtils.md5DigestAsHex(userRequest.getPassword().getBytes()));
		user.setEnabled(true);//用户可用
		user.setEmail(userRequest.getEmail());
		long result = userMapper.reg(user);
		if (result == 1) {
			return 0;
		} else {
			return 3;
		}
	}

	public boolean auth(User user, String password) {
		String passwordMD5 = DigestUtils.md5DigestAsHex(password.getBytes());
		logger.info("传入密码" + passwordMD5);
		return passwordMD5.equals(user.getPassword());
	}


	public User searchUserByUsername(String username) {
		return userMapper.loadUserByUsername(username);
	}

}
