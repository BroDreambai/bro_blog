package com.bro.blog.base.bean;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dreambai on 2017/12/20.
 */
@Data
public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String title; //标题
	private String mdContent;
	private String htmlContent;
	private String summary;
	private Long cid;
	private Long uid;
	private Timestamp publishDate;
	private Integer state;
	private Integer pageView;
	private Timestamp editTime;
	private String[] dynamicTags;
	private String nickname;
	private String cateName;
	private List<Tags> tags;
	private String stateStr;

}
