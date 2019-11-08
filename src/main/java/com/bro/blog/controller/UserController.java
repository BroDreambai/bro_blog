package com.bro.blog.controller;

import com.bro.blog.base.request.UserRequest;
import com.bro.blog.service.UserService;
import com.dreambai.api.pojo.JsonResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

	@Resource
	private UserService userService;

	@PostMapping("/reg")
	public JsonResult<String> reg(@RequestBody @Validated UserRequest userRequest) {
		if (userRequest.getUsername() == null &&
				userRequest.getPassword() == null) {
			return JsonResult.error("用户名或密码不能为空");
		}
		int result = userService.reg(userRequest);

		if (result == 0) {
			//成功
			return JsonResult.success("注册成功");
		}
		if (result == 1) {
			return JsonResult.error("用户名已被注册，注册失败!");
		}
		if (result == 2) {
			//失败
			return JsonResult.error("邮箱已被注册，注册失败!");
		}
		return JsonResult.error("注册失败!");
	}




}
