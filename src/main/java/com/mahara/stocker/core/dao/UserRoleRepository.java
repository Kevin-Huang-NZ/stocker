package com.mahara.stocker.core.dao;

import com.mahara.stocker.core.model.UserRole;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
  List<UserRole> findByUserId(Long userId);

  @Modifying
  @Query(
      value = "delete from user_role where user_id=:userId and role_id=:roleId",
      nativeQuery = true)
  void deleteByUniqueKey(@Param("userId") Long userId, @Param("roleId") Long roleId);

  @Modifying
  @Query(value = "delete from user_role where user_id=:userId", nativeQuery = true)
  void deleteByUser(@Param("userId") Long userId);

  @Modifying
  @Query(value = "delete from user_role where role_id=:roleId", nativeQuery = true)
  void deleteByRole(@Param("roleId") Long roleId);
}
