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
public class SysRolePermission {
  public interface Create extends Default {}

  public interface Update extends Default {}

  public SysRolePermission(Long roleId, Long permissionId) {
    this.roleId = roleId;
    this.permissionId = permissionId;
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

  @NotNull(message = "请选择角色。")
  private Long roleId;

  @NotNull(message = "请选择权限。")
  private Long permissionId;
}
