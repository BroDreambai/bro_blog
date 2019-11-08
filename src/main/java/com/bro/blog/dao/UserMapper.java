package com.bro.blog.dao;


import com.bro.blog.base.bean.Role;
import com.bro.blog.base.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

	User loadUserByUsername(@Param("username") String username);

	int checkUsernameExist(@Param("username") String username);

	int checkEmailExist(@Param("email") String email);

	long reg(User user);

	int updateUserEmail(@Param("email") String email, @Param("id") Long id);

	List<User> getUserByNickname(@Param("nickname") String nickname);

	List<Role> getAllRole();

	int updateUserEnabled(@Param("enabled") Boolean enabled, @Param("uid") Long uid);

	int deleteUserById(Long uid);

	int deleteUserRolesByUid(Long id);

	int setUserRoles(@Param("rids") Long[] rids, @Param("id") Long id);

	User getUserById(@Param("id") Long id);
}
