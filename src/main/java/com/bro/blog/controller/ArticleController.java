package com.bro.blog.controller;


import com.bro.blog.base.bean.Article;
import com.bro.blog.base.request.ArticleRequest;
import com.bro.blog.service.ArticleService;
import com.bro.blog.util.JwtUtil;
import com.dreambai.api.pojo.JsonResult;
import com.dreambai.api.pojo.page.Pager;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dreambai on 2017/12/20.
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");


	@Resource
	ArticleService articleService;

	@PostMapping("/")
	public JsonResult<String> addNewArticle(Article article, HttpServletRequest request) {
		Long uid = Long.valueOf(String.valueOf(request.getAttribute("uid")));
		article.setUid(uid);
		int result = articleService.addNewArticle(article);
		if (result == 1) {
			return JsonResult.success(article.getId() + "");
		} else {
			return JsonResult.error(article.getState() == 0 ? "文章保存失败!" : "文章发表失败!");
		}
	}

	/**
	 * 上传图片
	 *
	 * @return 返回值为图片的地址
	 */
	@PostMapping("/uploadimg")
	public JsonResult<String> uploadImg(HttpServletRequest req, MultipartFile image) {
		StringBuffer url = new StringBuffer();
		String filePath = "/blogimg/" + sdf.format(new Date());
		String imgFolderPath = req.getServletContext().getRealPath(filePath);
		File imgFolder = new File(imgFolderPath);
		if (!imgFolder.exists()) {
			imgFolder.mkdirs();
		}
		url.append(req.getScheme())
				.append("://")
				.append(req.getServerName())
				.append(":")
				.append(req.getServerPort())
				.append(req.getContextPath())
				.append(filePath);
		String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
		try {
			IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
			url.append("/").append(imgName);
			return JsonResult.success(url.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return JsonResult.error("上传失败!");
	}

	@PostMapping("/all")
	public Pager<Article> getArticleByState(@RequestBody ArticleRequest req, HttpServletRequest request) {

		Long uid = Long.valueOf(String.valueOf(request.getAttribute("uid")));

		int totalCount = articleService.getArticleCount(uid, req);
		if (totalCount == 0) {
			return new Pager<>(
					new Pager.PageData(req.getPage().getPageNo(), req.getPage().getPageSize(), totalCount),
					Collections.emptyList());
		}

		List<Article> articles = articleService.getArticleByKeywords(req, uid);
		return new Pager<>(
				new Pager.PageData(req.getPage().getPageNo(), req.getPage().getPageSize(), totalCount),
				articles);
	}

	@GetMapping("/{aid}")
	public JsonResult<Article> getArticleById(@PathVariable Long aid, HttpServletRequest request) {
		Long uid = Long.valueOf(String.valueOf(request.getAttribute("uid")));
		Article article = articleService.getArticleById(aid, uid);
		return JsonResult.success(article);
	}


	@RequestMapping(value = "/dustbin", method = RequestMethod.PUT)
	public JsonResult<String> updateArticleState(Long[] aids, Integer state) {
		if (articleService.updateArticleState(aids, state) == aids.length) {
			return JsonResult.success("删除成功!");
		}
		return JsonResult.error("删除失败!");
	}

}
