package com.bro.blog.controller;

import com.bro.blog.base.model.TokenModel;
import com.bro.blog.base.request.UserRequest;
import com.bro.blog.service.LoginService;
import com.dreambai.api.pojo.JsonResult;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Resource
	LoginService loginService;


	@PostMapping("/login")
	public JsonResult<TokenModel> login(@RequestBody UserRequest userRequest) {
		String userName = userRequest.getUsername();
		String password = userRequest.getPassword();
		if (StringUtils.isBlank(userName)) {
			JsonResult.error("账户名不能为空");
		}

		if (StringUtils.isBlank(password)) {
			JsonResult.error("密码不能为空");
		}
		TokenModel tokenModel = loginService.login(userName, password);
		if (Objects.isNull(tokenModel)) {
			return JsonResult.error("账户名或密码错误");
		}
		return JsonResult.success(tokenModel);

	}


	@GetMapping("/logout")
	public JsonResult<String> logout(HttpServletRequest request) {
		String token = request.getHeader("token");
		boolean b = loginService.logout(token);
		return b ? JsonResult.success("注销成功") : JsonResult.error("注销失败");
	}

}
