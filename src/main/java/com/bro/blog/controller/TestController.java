package com.bro.blog.controller;

import com.dreambai.api.pojo.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

	@GetMapping("/a")
	public JsonResult<String> a() {
		return JsonResult.success("你成功越权了！");
	}

	@GetMapping("/z")
	public JsonResult<String> z() {
		return JsonResult.success("这个接口在你的权限之内！");
	}
}
