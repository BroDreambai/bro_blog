package com.bro.blog.base.request;

import com.dreambai.api.pojo.page.PageRequest;
import lombok.Data;


@Data
public class ArticleRequest extends PageRequest {

	private static final long serialVersionUID = 1L;

	int state = 0;

	String keywords = "";

}
