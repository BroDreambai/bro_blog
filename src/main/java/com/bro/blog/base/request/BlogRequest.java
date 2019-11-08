package com.bro.blog.base.request;

import com.dreambai.api.pojo.page.PageRequest;
import lombok.Data;

/**
 * Created by hang.zhao on 2019/1/27.
 */
@Data
public class BlogRequest extends PageRequest {

	private static final long serialVersionUID = 1L;

	public String cateName = "";

	public String keywords = "";
}
