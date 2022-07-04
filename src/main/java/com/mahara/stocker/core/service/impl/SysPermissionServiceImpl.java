package com.mahara.stocker.core.service.impl;

import com.mahara.stocker.core.dao.SysPermissionRepository;
import com.mahara.stocker.core.dao.SysRolePermissionRepository;
import com.mahara.stocker.core.model.SysPermission;
import com.mahara.stocker.core.service.SysPermissionService;
import com.mahara.stocker.error.CustomizedException;
import com.mahara.stocker.error.PredefinedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SysPermissionServiceImpl implements SysPermissionService {
  @Autowired private SysRolePermissionRepository rolePermissionRepository;
  @Autowired private SysPermissionRepository permissionRepository;

  @Transactional
  @Override
  public void deletePermission(Long id) {
    if (permissionRepository.existsById(id)) {
      permissionRepository.deleteById(id);
      rolePermissionRepository.deleteByPermission(id);
    }
  }

  @Override
  public SysPermission createPermission(SysPermission entity) throws CustomizedException {
    var checkExist = permissionRepository.findByUniqueKey(entity.getPermission(), 0l);
    if (checkExist.isPresent()) {
      throw new CustomizedException(PredefinedError.DATA_NOT_EXIST, "权限重复，保存失败。");
    } else {
      return permissionRepository.save(entity);
    }
  }

  @Override
  public SysPermission updatePermission(SysPermission entity) throws CustomizedException {
    var checkExist = permissionRepository.findByUniqueKey(entity.getPermission(), entity.getId());
    if (checkExist.isPresent()) {
      throw new CustomizedException(PredefinedError.DATA_NOT_EXIST, "权限重复，保存失败。");
    } else {
      return permissionRepository.save(entity);
    }
  }
}
