package com.bro.blog.util;

import com.bro.blog.controller.LoginController;
import com.bro.blog.exception.ParamException;
import io.jsonwebtoken.*;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

public class JwtUtil {

	private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);

	private static final String ISSUER = "dream_bai";


	private static final String JWT_SECRET = "daoruaimifasaolaxi";

	private static final long EXPIRES_TIME = 7 * 24 * 3600 * 1000;


	public static String createJWT(long uid) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);
		Date expiresDate = new Date(nowMillis + EXPIRES_TIME);
		SecretKey secretKey = generalKey();
		JwtBuilder builder = Jwts.builder()
				.setSubject(String.valueOf(uid))   // 主题
				.setIssuer(ISSUER)     // 签发者
				.setIssuedAt(now)// 签发时间
				.setExpiration(expiresDate)// 过期时间
				.signWith(signatureAlgorithm, secretKey); // 签名算法以及密匙
		return builder.compact();
	}

	/**
	 * 验证JWT
	 *
	 * @param jwtStr
	 * @return
	 */
	public static String validateJWT(String jwtStr) {
		try {
			return parseJWT(jwtStr);
		} catch (SignatureException sex) {
			logger.error(sex.getMessage());
			throw new ParamException("token错误");
		}
	}


	private static SecretKey generalKey() {
		byte[] encodedKey = Base64.decode(JWT_SECRET);
		return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	}

	/**
	 * 解析JWT字符串
	 *
	 * @param jwt
	 * @return Claims
	 */
	public static String parseJWT(String jwt) {
		SecretKey secretKey = generalKey();
		return Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(jwt)
				.getBody().getSubject();
	}
}
