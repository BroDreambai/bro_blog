package com.bro.blog.base.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class UserRequest {
	@NotBlank(message = "账号不能为空")
	private String username;
	@NotBlank(message = "密码不能为空")
	private String password;
	@NotBlank(message = "昵称不能为空")
	private String nickname;
	@NotBlank(message = "邮箱不能为空")
	@Email(message = "错误的邮箱格式")
	private String email;
	
	private String userface;

}
