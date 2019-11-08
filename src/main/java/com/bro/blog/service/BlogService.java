package com.bro.blog.service;

import com.bro.blog.base.bean.Blog;
import com.bro.blog.base.bean.Category;
import com.bro.blog.base.request.BlogRequest;
import com.bro.blog.dao.BlogMapper;
import com.bro.blog.util.EhcacheUtil;
import com.dreambai.api.pojo.page.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class BlogService {


	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private BlogMapper blogMapper;

	public Pager<Blog> blogs(BlogRequest req) {
		req.getPage().setPageSize(10);
		int totalCount = blogMapper.getTotal(req);
		logger.info("totalCount:", totalCount);
		if (totalCount == 0) {
			return new Pager<>(
					new Pager.PageData(req.getPage().getPageNo(), req.getPage().getPageSize(), totalCount),
					Collections.emptyList());
		}

		List<Blog> resultData = blogMapper.selectBlogs(req);
		return new Pager<>(
				new Pager.PageData(req.getPage().getPageNo(), req.getPage().getPageSize(), totalCount),
				resultData);
	}

	public Blog getBlogById(Long aid, String ipAddress) {
		Blog blog = blogMapper.getBlogById(aid);
		if (!EhcacheUtil.foundCache(ipAddress, "ip")
				&& Objects.nonNull(blog)) {
			EhcacheUtil.setCache(ipAddress, 1, "ip");
			blogMapper.pvIncrement(aid);
		}
		return blog;
	}


	public List<Category> Categories(){
		return blogMapper.Categories();
	}


}
