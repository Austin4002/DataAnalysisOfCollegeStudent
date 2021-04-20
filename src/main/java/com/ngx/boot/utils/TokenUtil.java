package com.ngx.boot.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class TokenUtil {
	// 过期时间
	private static final long EXPIRE_TIME = 6 * 60 * 60 * 1000;//1小时
	private static final String TOKEN_SECRET = "ngx"; // 密钥盐

	/**
	 * 签名生成
	 * @param username
	 * @param password
	 * @return
	 */
	public static String sign(String username,String password) {
		String token = null;
		try {
			Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);
			token = JWT.create()
					.withIssuer("admin")
					.withClaim("username", username)
					.withExpiresAt(expiresAt)
					// 使用了HMAC256加密算法。
					.sign(Algorithm.HMAC256(TOKEN_SECRET));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}

	/**
	 * 签名验证
	 * 
	 * @param token
	 * @return
	 */
	public static boolean verify(String token) {
		try {
			JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("admin").build();
			DecodedJWT jwt = verifier.verify(token);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = jwt.getExpiresAt();
			log.error("认证成功,过期时间：------------->{}",dateFormat.format(date));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 解码
	 * @param token
	 * @return
	 */
	public static String decode(String token){
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).withIssuer("admin").build();
		DecodedJWT jwt = verifier.verify(token);
		String username = jwt.getClaim("username").asString();
		return username;
	}

}
