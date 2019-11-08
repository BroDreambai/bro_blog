package com.bro.blog.controller;


import com.bro.blog.base.bean.Blog;
import com.bro.blog.base.bean.Category;
import com.bro.blog.base.request.BlogRequest;
import com.bro.blog.service.BlogService;
import com.bro.blog.util.IpUtil;
import com.dreambai.api.pojo.JsonResult;
import com.dreambai.api.pojo.page.Pager;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hang.zhao on 2019/1/24.
 * 博客Controller
 */
@RestController
@RequestMapping("blog")
public class BlogController {

	@Resource
	BlogService blogService;


	//博客列表  分类检索  模糊搜索
	@PostMapping("/list")
	public Pager<Blog> blogs(@RequestBody BlogRequest req) {
		return blogService.blogs(req);
	}

	@GetMapping("/{aid}")
	public JsonResult<Blog> blog(@PathVariable Long aid, HttpServletRequest request) {
		String ipAddress = IpUtil.getIpAddress(request);
		return JsonResult.success(blogService.getBlogById(aid, ipAddress));
	}

	@GetMapping("/Categories")
	public JsonResult<List<Category>> Categories() {
		return JsonResult.success(blogService.Categories());
	}


}


