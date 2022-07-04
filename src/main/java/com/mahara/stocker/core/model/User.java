package com.mahara.stocker.core.model;

import com.mahara.stocker.util.ValidatorPattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class User implements Serializable {
  public interface Create extends Default {}

  public interface Update extends Default {}

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull(
      message = "请指定要修改的对象。",
      groups = {Update.class})
  @Null(
      message = "新建对象时，不能指定id。",
      groups = {Create.class})
  private Long id;

  @NotBlank(message = "请输入用户名。")
  @Length(max = 100, message = "用户名超长，最多50字符。")
  private String userName;

  @Length(max = 200, message = "头像URL超长，最多200字符。")
  private String avatar;

  @Pattern(regexp = "^[0,1,x]$", message = "性别选择范围：0-女；1-男；x-未提供。")
  private String gender = "x";

  @Pattern(regexp = ValidatorPattern.REGEX_YMD, message = "生日格式不正确，格式要求：yyyy-MM-dd。")
  private String birthday;

  @NotNull(message = "请输入手机号。")
  @Pattern(regexp = ValidatorPattern.REGEX_MOBILE, message = "手机号格式不正确。")
  private String phone;

  @NotNull(message = "请输入密码。")
  @Pattern(
      regexp = ValidatorPattern.REGEX_PASSWORD,
      message = "密码不满足安全要求。要求如下：1、密码需包含数字、大小写英文字母；2-可以包含符号._~!@#$^&*；3-长度在8~20位之间。")
  private String password;

  @Pattern(regexp = "^[0,1]$", message = "状态选择范围：0-冻结；1-正常。")
  private String status = "1";
}
