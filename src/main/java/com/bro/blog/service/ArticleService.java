package com.bro.blog.service;


import com.bro.blog.base.bean.Article;
import com.bro.blog.base.request.ArticleRequest;
import com.bro.blog.dao.ArticleMapper;
import com.bro.blog.dao.TagsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Created by dreambai on 2017/12/20.
 */
@Service
@Transactional
public class ArticleService {
	@Resource
	ArticleMapper articleMapper;
	@Resource
	TagsMapper tagsMapper;

	public int addNewArticle(Article article) {
		//处理文章摘要
		if (article.getSummary() == null || "".equals(article.getSummary())) {
			//直接截取
			String stripHtml = stripHtml(article.getHtmlContent());
			article.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
		}
		if (article.getId() == -1) {
			//添加操作
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			if (article.getState() == 1) {
				//设置发表日期
				article.setPublishDate(timestamp);
			}
			article.setEditTime(timestamp);
			//设置当前用户
//            article.setUid(Util.getCurrentUser().getId());
			int i = articleMapper.addNewArticle(article);
			//打标签
			String[] dynamicTags = article.getDynamicTags();
			if (dynamicTags != null && dynamicTags.length > 0) {
				int tags = addTagsToArticle(dynamicTags, article.getId());
				if (tags == -1) {
					return tags;
				}
			}
			return i;
		} else {
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			if (article.getState() == 1) {
				//设置发表日期
				article.setPublishDate(timestamp);
			}
			//更新
			article.setEditTime(new Timestamp(System.currentTimeMillis()));
			int i = articleMapper.updateArticle(article);
			//修改标签
			String[] dynamicTags = article.getDynamicTags();
			if (dynamicTags != null && dynamicTags.length > 0) {
				int tags = addTagsToArticle(dynamicTags, article.getId());
				if (tags == -1) {
					return tags;
				}
			}
			return i;
		}
	}

	private int addTagsToArticle(String[] dynamicTags, Long aid) {
		//1.删除该文章目前所有的标签
		tagsMapper.deleteTagsByAid(aid);
		//2.将上传上来的标签全部存入数据库
		tagsMapper.saveTags(dynamicTags);
		//3.查询这些标签的id
		List<Long> tIds = tagsMapper.getTagsIdByTagName(dynamicTags);
		//4.重新给文章设置标签
		int i = tagsMapper.saveTags2ArticleTags(tIds, aid);
		return i == dynamicTags.length ? i : -1;
	}

	public String stripHtml(String content) {
		content = content.replaceAll("<p .*?>", "");
		content = content.replaceAll("<br\\s*/?>", "");
		content = content.replaceAll("\\<.*?>", "");
		return content;
	}

	public List<Article> getArticleByKeywords(ArticleRequest articleRequest, long uid) {

		return articleMapper.getArticleByKeywords(articleRequest.getOffset(), articleRequest.getPage().getPageSize(), uid, articleRequest.getKeywords());
	}


	public int getArticleCount(Long uid, ArticleRequest articleRequest) {
		return articleMapper.getArticleCount(articleRequest.getState(), uid, articleRequest.getKeywords());
	}

	public int updateArticleState(Long[] aids, Integer state) {
		if (state == 2) {
			return articleMapper.deleteArticleById(aids);
		} else {
			return articleMapper.updateArticleState(aids, 2);//放入到回收站中
		}
	}

	public Article getArticleById(Long aid, Long uid) {

		Article article = articleMapper.getArticleById(aid, uid);

		return article;
	}

}
