package com.mahara.stocker.core.service;

import com.mahara.stocker.core.model.SysPermission;
import com.mahara.stocker.error.CustomizedException;

public interface SysPermissionService {
  void deletePermission(Long id);

  SysPermission createPermission(SysPermission entity) throws CustomizedException;

  SysPermission updatePermission(SysPermission entity) throws CustomizedException;
}
