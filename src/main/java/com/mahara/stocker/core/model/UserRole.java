package com.mahara.stocker.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.groups.Default;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
  public interface Create extends Default {}

  public interface Update extends Default {}

  public UserRole(Long userId, Long roleId) {
    this.userId = userId;
    this.roleId = roleId;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @NotNull(
      message = "请指定要修改的对象。",
      groups = {Update.class})
  @Null(
      message = "新建对象时，不能指定id。",
      groups = {Create.class})
  private Long id;

  @NotNull(message = "请选择用户。")
  private Long userId;

  @NotNull(message = "请选择角色。")
  private Long roleId;
}
