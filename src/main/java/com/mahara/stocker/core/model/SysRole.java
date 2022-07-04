package com.mahara.stocker.core.model;

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

@Entity
@Data
@NoArgsConstructor
public class SysRole {
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

  @Pattern(regexp = "^[a-zA-Z0-9-_]{1,50}$", message = "角色格式不正确。格式要求：由字母、数字、中划线或下划线组成；长度在1~50之间。")
  private String role;

  @NotBlank(message = "角色名称不能为空。")
  @Length(max = 100, message = "角色名称超长，最多100字符。")
  private String roleName;

  @Length(max = 500, message = "备注超长，最多500字符。")
  private String memo;
}
