package com.mahara.stocker.util;

import com.alibaba.fastjson.JSONObject;
import com.mahara.stocker.core.dao.UserRepository;
import com.mahara.stocker.security.JwtUtil;
import com.mahara.stocker.security.PasswordEncoderUtil;
import com.mahara.stocker.web.request.PaginationIn;
import com.mahara.stocker.web.vo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class MockDataFactory {
  public static final String ADMIN_USER_NAME = "13811650908";
  public static final String ADMIN_USER_PASSWORD = "1234Qwer";

  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private UserRepository userRepository;

  @Cacheable("login-user-cache-for-test")
  public LoginUser loginUser() {
    LoginUser loginUser = new LoginUser();
    loginUser.setUserName(ADMIN_USER_NAME);
    loginUser.setPassword(ADMIN_USER_PASSWORD);

    return loginUser;
  }

  @Cacheable("jwt-cache-for-test")
  public String jwt() {
    var user = userRepository.findByUniqueKey(ADMIN_USER_NAME, null);
    return "Bearer " + jwtUtil.sign(ADMIN_USER_NAME, user.get().getPassword());
  }

//  public JSONObject queryParams(Optional<PaginationIn> pagenationIn) {
//    JSONObject params = new JSONObject();
//    if (pagenationIn.isPresent()) {
//      params.put("number", pagenationIn.get().getNumber());
//      params.put("size", pagenationIn.get().getSize());
//    }
//    return params;
//  }
}
