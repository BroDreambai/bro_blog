package com.bro.blog.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class TokenModel {
	private String token;
	private int rid;
}
