package com.mahara.stocker.core.dao;

import com.mahara.stocker.core.model.SysPermission;
import org.springframework.data.repository.CrudRepository;

public interface SysPermissionRepository
    extends CrudRepository<SysPermission, Long>, SysPermissionJdbc {}
