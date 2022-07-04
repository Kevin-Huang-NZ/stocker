package com.mahara.stocker.core.service.impl;

import com.mahara.stocker.core.dao.SysRolePermissionRepository;
import com.mahara.stocker.core.dao.SysRoleRepository;
import com.mahara.stocker.core.dao.UserRoleRepository;
import com.mahara.stocker.core.model.SysRole;
import com.mahara.stocker.core.model.SysRolePermission;
import com.mahara.stocker.core.service.SysRoleService;
import com.mahara.stocker.error.CustomizedException;
import com.mahara.stocker.error.PredefinedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SysRoleServiceImpl implements SysRoleService {
  @Autowired private SysRolePermissionRepository rolePermissionRepository;
  @Autowired private SysRoleRepository roleRepository;
  @Autowired private UserRoleRepository userRoleRepository;

  @Override
  public void addPermission(SysRolePermission entity) {
    rolePermissionRepository.save(entity);
  }

  @Override
  public void removePermission(SysRolePermission entity) {
    rolePermissionRepository.deleteByUniqueKey(entity.getRoleId(), entity.getPermissionId());
  }

  @Transactional
  @Override
  public void deleteRole(Long id) {
    if (roleRepository.existsById(id)) {
      roleRepository.deleteById(id);
      rolePermissionRepository.deleteByRole(id);
      userRoleRepository.deleteByRole(id);
    }
  }

  @Override
  public SysRole createRole(SysRole entity) throws CustomizedException {
    var checkExist = roleRepository.findByUniqueKey(entity.getRole(), 0l);
    if (checkExist.isPresent()) {
      throw new CustomizedException(PredefinedError.DATA_NOT_EXIST, "角色重复，保存失败。");
    } else {
      return roleRepository.save(entity);
    }
  }

  @Override
  public SysRole updateRole(SysRole entity) throws CustomizedException {
    var checkExist = roleRepository.findByUniqueKey(entity.getRole(), entity.getId());
    if (checkExist.isPresent()) {
      throw new CustomizedException(PredefinedError.DATA_NOT_EXIST, "角色重复，保存失败。");
    } else {
      return roleRepository.save(entity);
    }
  }
}
