package com.bro.blog.dao;

import com.bro.blog.base.bean.Link;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by hang.zhao on 2019/1/26.
 */
@Mapper
public interface LinkMapper {

	List<Link> allLinkList();


}
