package com.mahara.stocker.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 在UserService和SpringSecurity中都使用到了密码加密，所以定义这个类。
 * 如果只在SpringSecurity中使用，则可以直接在passwordEncoder()中new
 */
@Component
public class PasswordEncoderUtil {
  private PasswordEncoder passwordEncoder;
  public PasswordEncoderUtil(){
    this.passwordEncoder = new BCryptPasswordEncoder();
  }

  public PasswordEncoder getPasswordEncoder() {
    return passwordEncoder;
  }

  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }
}
