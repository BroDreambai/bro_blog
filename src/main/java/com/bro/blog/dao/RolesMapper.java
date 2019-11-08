package com.bro.blog.dao;

import com.bro.blog.base.bean.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * Created by dreambai on 2017/12/17.
 */
@Mapper
public interface RolesMapper {
    int addRole(@Param("roles") String role, @Param("uid") Long uid);

    Role getRoleByUid(Long uid);
}
