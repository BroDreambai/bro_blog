package com.bro.blog.base.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by dreambai on 2017/12/21.
 */
@Data
public class Tags implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String tagName;
}
