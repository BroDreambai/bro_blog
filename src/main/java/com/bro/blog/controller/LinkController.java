package com.bro.blog.controller;

import com.bro.blog.base.bean.Link;
import com.bro.blog.service.LinkService;
import com.dreambai.annotation.JsonResponseBody;
import com.dreambai.api.pojo.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


@RestController
@JsonResponseBody
@RequestMapping("/link")
public class LinkController {

	@Resource
	LinkService linkService;

	@GetMapping("/list")
	public JsonResult<List<Link>> LinkList() {

		return JsonResult.success(linkService.allLinkList());
	}


}
