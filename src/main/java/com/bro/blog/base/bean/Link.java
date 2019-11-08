package com.bro.blog.base.bean;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by hang.zhao on 2019/1/26.
 */
@Data
public class Link implements Serializable {


    private static final long serialVersionUID = 1L;

    String linkName;
    String linkUrl;
}
