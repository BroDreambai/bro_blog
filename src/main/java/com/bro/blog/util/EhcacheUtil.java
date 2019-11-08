package com.bro.blog.util;

import com.bro.blog.controller.LoginController;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class EhcacheUtil {

	private static Logger logger = LoggerFactory.getLogger(EhcacheUtil.class);


	@Autowired
	private static CacheManager cacheManager = CacheManager.create("./src/main/resources/ehcache.xml");


	/**
	 * 设置缓存对象
	 */
	public static void setCache(Object key, Object value, String cacheName) {
		Element element = new Element(key, value);
		Cache cache = cacheManager.getCache(cacheName);
		cache.put(element);
	}

	/**
	 * 从缓存中取出对象
	 *
	 * @param key
	 * @return
	 */
	public static Object getCache(Object key, String cacheName) {
		Cache cache = cacheManager.getCache(cacheName);
		if (Objects.nonNull(cache.get(key))) {
			return cache.get(key).getObjectValue();
		}
		throw new CacheException("缓存不存在");
	}



	public static boolean foundCache(Object key, String cacheName) {

		try {
			EhcacheUtil.getCache(key, cacheName);
			return true;
		} catch (CacheException cex) {
			logger.info(cex.getMessage());
			return false;
		}

	}


	public static void removeCache(Object key, String cacheName) throws CacheException {
		EhcacheUtil.getCache(key, cacheName);
		Cache cache = cacheManager.getCache(cacheName);
		cache.remove(key);
	}
}