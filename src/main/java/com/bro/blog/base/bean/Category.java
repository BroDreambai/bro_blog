package com.bro.blog.base.bean;

import lombok.Data;

import java.io.Serializable;


@Data
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;

	private String cateName; // 类型名称


}
