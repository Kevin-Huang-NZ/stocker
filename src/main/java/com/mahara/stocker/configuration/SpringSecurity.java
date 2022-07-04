package com.mahara.stocker.configuration;

import com.mahara.stocker.security.JwtAuthenticationFilter;
import com.mahara.stocker.security.JwtAuthorizationFilter;
import com.mahara.stocker.security.JwtUtil;
import com.mahara.stocker.security.PasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurity extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private CacheManager cacheManager;
  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private PasswordEncoderUtil passwordEncoderUtil;

  @Bean
  public PasswordEncoder passwordEncoder(){
    return this.passwordEncoderUtil.getPasswordEncoder();
  }

  @Bean
  public CachingUserDetailsService cachingUserDetailsService() {
    return new CachingUserDetailsService(this.userDetailsService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 开启跨域
    http.cors()
        .and()
        // security 默认 csrf 是开启的，我们使用了 token ，这个也没有什么必要了
        .csrf().disable()
        .authorizeRequests()
        // 默认所有请求通过，但是我们要在需要权限的方法加上安全注解，这样比写死配置灵活很多
        .anyRequest().permitAll()
        .and()
        // 添加自己编写的两个过滤器
//        .addFilter(new JwtAuthenticationFilter(authenticationManager(), this.jwtUtil))
        .addFilter(new JwtAuthorizationFilter(authenticationManager(), cachingUserDetailsService(), this.jwtUtil))
        // 前后端分离是 STATELESS，故 session 使用该策略
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  // 此处配置 AuthenticationManager，并且实现缓存
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    // 对自己编写的 UserDetailsServiceImpl 进一步包装，实现缓存
    CachingUserDetailsService cachingUserDetailsService = cachingUserDetailsService();
    // jwt-cache 我们在 ehcache.xml 配置文件中有声明
    UserCache userCache = new SpringCacheBasedUserCache(cacheManager.getCache("jwt-cache"));
    cachingUserDetailsService.setUserCache(userCache);
    /*
    security 默认鉴权完成后会把密码抹除，但是这里我们使用用户的密码来作为 JWT 的生成密钥，
    如果被抹除了，在对 JWT 进行签名的时候就拿不到用户密码了，故此处关闭了自动抹除密码。
     */
//    auth.eraseCredentials(false);
    auth.userDetailsService(cachingUserDetailsService);
  }
}
