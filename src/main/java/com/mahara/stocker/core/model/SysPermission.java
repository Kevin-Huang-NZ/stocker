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
public class SysPermission {
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

  @Pattern(
      regexp = "^[a-zA-Z][a-zA-Z0-9-_]*:[a-zA-Z\\*][a-zA-Z0-9-_]*$",
      message = "权限格式不正确。格式要求：page:action；其中page和action由字母、数字、中划线或下划线组成，action可以使用*表使所有页面功能。")
  @Length(max = 100, message = "权限超长，最多100字符。")
  private String permission;

  @NotBlank(message = "权限名称不能为空。")
  @Length(max = 100, message = "权限名称超长，最多100字符。")
  private String permissionName;

  @Length(max = 500, message = "备注超长，最多500字符。")
  private String memo;
}
