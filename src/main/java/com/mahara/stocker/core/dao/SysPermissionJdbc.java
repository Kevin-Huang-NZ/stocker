package com.mahara.stocker.core.dao;

import com.mahara.stocker.core.model.SysPermission;
import com.mahara.stocker.web.request.PaginationIn;
import com.mahara.stocker.web.response.PaginationOut;

import java.util.List;
import java.util.Optional;

public interface SysPermissionJdbc {
  PaginationOut<SysPermission> search(String keyword, PaginationIn pi);

  List<SysPermission> getByRoleIds(List<Long> roleIds);

  Optional<SysPermission> findByUniqueKey(String permission, Long id);
}
