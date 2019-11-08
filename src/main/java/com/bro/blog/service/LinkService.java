package com.bro.blog.service;

import com.bro.blog.base.bean.Link;
import com.bro.blog.dao.LinkMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LinkService {

	@Resource
	LinkMapper linkMapper;

	public List<Link> allLinkList() {
		return linkMapper.allLinkList();
	}
}
