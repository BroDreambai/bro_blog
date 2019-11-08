package com.bro.blog.filter;


import com.bro.blog.base.model.TokenModel;
import com.bro.blog.util.EhcacheUtil;
import com.bro.blog.util.JwtUtil;
import net.sf.ehcache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = "*", filterName = "AuthenticationFilter")
public class AuthenticationFilter extends OncePerRequestFilter {
	private Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);


	@Value("${admin.paths}")
	private List<String> adminPath;

	@Value("${pass.paths}")
	private List<String> passPaths;

	private static String AUTH_CACHE_NAME = "auth";


	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

		String servletPath = httpServletRequest.getServletPath();
		TokenModel model = null;

		// 请求的路径是排除的URL时，则直接放行


		if (isPass(servletPath)) {
			try {
				filterChain.doFilter(httpServletRequest, httpServletResponse);
			} catch (Throwable e) {
				logger.error("doFilter 异常：", e);
			}
			return;
		}
		String token = httpServletRequest.getHeader("token");
		logger.info("token:{}", token);
		if (token == null) {
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		String uid = JwtUtil.validateJWT(token);
		try {
			model = (TokenModel) EhcacheUtil.getCache(uid, AUTH_CACHE_NAME);
		} catch (CacheException cex) {
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		}
		if (model == null) {
			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		int role = model.getRid();
		if (role == 2 && adminPath.contains(servletPath)) {
			httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		httpServletRequest.setAttribute("uid", uid);
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}


	@Override
	public void destroy() {
		logger.info("AccesLogFilter destroy");
	}


	private boolean isPass(String servletPath) {

		return passPaths.stream().anyMatch(passPath -> Pattern.matches(passPath, servletPath));
	}
}
