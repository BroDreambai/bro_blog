package com.bro.blog.dao;

import com.bro.blog.base.bean.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by dreambai on 2017/12/20.
 */
@Mapper
public interface ArticleMapper {
	int addNewArticle(Article article);

	int updateArticle(Article article);

	List<Article> getArticleByKeywords(@Param("offset") Integer offset, @Param("count") Integer count, @Param("uid") Long uid, @Param("keywords") String keywords);

//    List<Article> getArticleByStateByAdmin(@Param("start") int start, @Param("count") Integer count, @Param("keywords") String keywords);

	int getArticleCount(@Param("state") Integer state, @Param("uid") Long uid, @Param("keywords") String keywords);

	int updateArticleState(@Param("aids") Long aids[], @Param("state") Integer state);

	int deleteArticleById(@Param("aids") Long[] aids);

	Article getArticleById(Long aid,Long uid);


}
