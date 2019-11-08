package com.bro.blog.base.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String password;
	private String nickname;
	private boolean enabled;
	private int rid;
	private String email;
	private String userface;
	private Timestamp createTime;
	private Timestamp updateTime;


}
