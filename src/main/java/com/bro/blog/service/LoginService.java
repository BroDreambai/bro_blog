package com.bro.blog.service;


import com.bro.blog.base.bean.User;
import com.bro.blog.base.model.TokenModel;
import com.bro.blog.exception.ParamException;
import com.bro.blog.util.EhcacheUtil;
import com.bro.blog.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class LoginService {


	private static String AUTH_CACHE_NAME = "auth";

	@Resource
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(LoginService.class);


	public TokenModel login(String userName, String password) {
		User user = userService.searchUserByUsername(userName);
		if (Objects.isNull(user)) {
			throw new ParamException("用户不存在！");
		}
		if (!userService.auth(user, password)) {
			throw new ParamException("密码错误！");
		}
		String token = JwtUtil.createJWT(user.getId());
		TokenModel tokenModel = new TokenModel(token, user.getRid());
		EhcacheUtil.setCache(String.valueOf(user.getId()), tokenModel, AUTH_CACHE_NAME);
		return tokenModel;
	}


	public boolean logout(String token) {
		try {
			String userName = JwtUtil.validateJWT(token);
			EhcacheUtil.removeCache(userName, AUTH_CACHE_NAME);
			return true;
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return false;
		}


	}

}
