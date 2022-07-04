package com.mahara.stocker.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mahara.stocker.core.model.User;
import com.mahara.stocker.error.PredefinedError;
import com.mahara.stocker.util.ResponseUtil;
import com.mahara.stocker.web.response.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Deprecated
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
  private JwtUtil jwtUtil;
  /*
  过滤器一定要设置 AuthenticationManager，所以此处我们这么编写，这里的 AuthenticationManager
  我会从 Security 配置的时候传入
  */
  public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        /*
        运行父类 UsernamePasswordAuthenticationFilter 的构造方法，能够设置此滤器指定
        方法为 POST [\login]
        */
    super();
    setAuthenticationManager(authenticationManager);
    this.jwtUtil = jwtUtil;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    try {
      var user = new ObjectMapper().readValue(request.getInputStream(), User.class);
      var token = new UsernamePasswordAuthenticationToken(user.getPhone(), user.getPassword());
      setDetails(request, token);
      return getAuthenticationManager().authenticate(token);
    } catch (IOException e) {
      log.error("登录信息读取失败。", e);
      throw new BadCredentialsException("登录信息读取失败。");
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult)
      throws IOException, ServletException {
    var user = (SecurityUser) authResult.getPrincipal();
    var jwt = this.jwtUtil.sign(user.getUsername(), user.getPassword());
    ResponseUtil.out(request, response, Root.create(PredefinedError.UNAUTHORIZED));
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
      throws IOException, ServletException {
    ResponseUtil.out(request, response, Root.create(PredefinedError.UNAUTHORIZED));
  }
}
