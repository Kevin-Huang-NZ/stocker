package com.mahara.stocker.core.dao;

import com.mahara.stocker.core.model.SysRole;
import com.mahara.stocker.web.request.PaginationIn;
import com.mahara.stocker.web.response.PaginationOut;

import java.util.List;
import java.util.Optional;

public interface SysRoleJdbc {
  PaginationOut<SysRole> search(String keyword, PaginationIn pi);

  List<SysRole> getByUserId(Long userId);

  Optional<SysRole> findByUniqueKey(String roleName, Long id);
}
