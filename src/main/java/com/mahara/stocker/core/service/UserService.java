package com.mahara.stocker.core.service;

import com.mahara.stocker.core.model.SysPermission;
import com.mahara.stocker.core.model.SysRole;
import com.mahara.stocker.core.model.User;
import com.mahara.stocker.core.model.UserRole;
import com.mahara.stocker.error.CustomizedException;

import java.util.List;
import java.util.Optional;

public interface UserService {
  void addRole(UserRole entity);

  void removeRole(UserRole entity);

  void deleteUser(Long id);

  Optional<User> findByUniqueKey(String phone);
  //    Optional<User> checkPhone(String phone, Long id);
  List<SysRole> getUserRoles(Long userId);

  List<SysPermission> getUserPermissions(List<Long> roleIds);

  User createUser(User entity) throws CustomizedException;

  User updateUser(User entity) throws CustomizedException;
}
