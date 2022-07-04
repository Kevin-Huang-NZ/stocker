package com.mahara.stocker.core.dao;

import com.mahara.stocker.core.model.SysRolePermission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysRolePermissionRepository extends CrudRepository<SysRolePermission, Long> {
  List<SysRolePermission> findByRoleId(Long roleId);

  @Modifying
  @Query(
      value = "delete from roles_permission where role_id=:roleId and permission_id=:permissionId",
      nativeQuery = true)
  void deleteByUniqueKey(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

  @Modifying
  @Query(value = "delete from roles_permission where role_id=:roleId", nativeQuery = true)
  void deleteByRole(@Param("roleId") Long roleId);

  @Modifying
  @Query(
      value = "delete from roles_permission where permission_id=:permissionId",
      nativeQuery = true)
  void deleteByPermission(@Param("permissionId") Long permissionId);
}
