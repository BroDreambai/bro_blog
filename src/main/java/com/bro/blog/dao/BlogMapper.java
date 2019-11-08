package com.bro.blog.dao;

import com.bro.blog.base.bean.Blog;
import com.bro.blog.base.bean.Category;
import com.bro.blog.base.request.BlogRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {

	int getTotal(BlogRequest request);

	List<Blog> selectBlogs(BlogRequest request);

	Blog getBlogById(Long aid);

	int pvIncrement(Long aid);

	List<Category> Categories();
}
