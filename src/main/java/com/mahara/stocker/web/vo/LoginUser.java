package com.mahara.stocker.web.vo;

import com.mahara.stocker.util.ValidatorPattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
  @NotNull(message = "请输入手机号。")
  @Pattern(regexp = ValidatorPattern.REGEX_MOBILE, message = "手机号格式不正确。")
  private String userName;
  @NotBlank(message = "请输入密码。")
  private String password;
}
