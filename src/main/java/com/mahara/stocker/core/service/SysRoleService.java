package com.mahara.stocker.core.service;

import com.mahara.stocker.core.model.SysRole;
import com.mahara.stocker.core.model.SysRolePermission;
import com.mahara.stocker.error.CustomizedException;

public interface SysRoleService {
  void addPermission(SysRolePermission entity);

  void removePermission(SysRolePermission entity);

  void deleteRole(Long id);

  SysRole createRole(SysRole entity) throws CustomizedException;

  SysRole updateRole(SysRole entity) throws CustomizedException;
}
