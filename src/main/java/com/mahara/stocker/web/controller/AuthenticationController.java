package com.mahara.stocker.web.controller;

import com.mahara.stocker.core.dao.UserRepository;
import com.mahara.stocker.core.model.User;
import com.mahara.stocker.core.service.UserService;
import com.mahara.stocker.error.CustomizedException;
import com.mahara.stocker.error.PredefinedError;
import com.mahara.stocker.security.JwtUtil;
import com.mahara.stocker.security.PasswordEncoderUtil;
import com.mahara.stocker.web.response.Root;
import com.mahara.stocker.web.vo.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户登录")
@RestController
@RequestMapping("/api")
@Slf4j
public class AuthenticationController {
  @Autowired private UserRepository userRepository;
  @Autowired private UserService userService;
  @Autowired private JwtUtil jwtUtil;
  @Autowired private PasswordEncoderUtil passwordEncoderUtil;

  @Operation(summary = "用户登录")
  @PostMapping("/login")
  public Root login(@RequestBody @Validated LoginUser entity)
      throws CustomizedException {
    log.debug("Username: {}, password: {}", entity.getUserName(), entity.getPassword());
    var user = userService.findByUniqueKey(entity.getUserName());
    if (user.isEmpty()) {
      throw new CustomizedException(PredefinedError.UNAUTHORIZED, "手机号和密码不匹配，登录失败。");
    }
    log.debug("User from db, password: {}", user.get().getPassword());
    if (!passwordEncoderUtil.getPasswordEncoder().matches(entity.getPassword(), user.get().getPassword())) {
        throw new CustomizedException(PredefinedError.UNAUTHORIZED, "手机号和密码不匹配，登录失败。");
    }

    return Root.create(jwtUtil.sign(user.get().getPhone(), user.get().getPassword()));
  }
}
