package com.mahara.stocker.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

/**
 * JWT工具类
 * 提供签名和验证等方法
 */
@Component(value = "jwtUtil")
@Slf4j
public class JwtUtil {
  Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  private static final String CLAIM_NAME = "identification";

  @Value("${jwt.expiration:28800}")
  public Long expiration;

  /**
   * Generate signature , Expired in {expiration} milliseconds
   *
   * @param identification user's phone
   * @param secret user's password
   * @return encrypted token
   */
  public String sign(String identification, String secret) {
    try {
      var date = Date.from(Instant.now().plusSeconds(expiration));
      var algorithm = Algorithm.HMAC256(secret);
      return JWT.create().withClaim(CLAIM_NAME, identification).withExpiresAt(date).sign(algorithm);
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Check whether the token is correct
   *
   * @param token token key
   * @param identification user's phone
   * @param secret user's password
   * @return
   */
  public boolean verify(String token, String identification, String secret) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWTVerifier verifier = JWT.require(algorithm).withClaim(CLAIM_NAME, identification).build();
      DecodedJWT jwt = verifier.verify(token);
      return true;
    } catch (Exception exception) {
//      log.error("token: {}, identification: {}, secret: {}", token, identification, secret, exception);
      return false;
    }
  }

  /**
   * The information in the token can be obtained without secret decryption Phone contained in @
   * return token
   */
  public String getIdentification(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim(CLAIM_NAME).asString();
    } catch (JWTDecodeException e) {
      return null;
    }
  }

  public boolean canRefresh(String token, Date lastPasswordReset) {
    return false;
  }

  public String refresh(String token) {
    return null;
  }
}
