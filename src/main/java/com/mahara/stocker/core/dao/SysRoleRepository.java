package com.mahara.stocker.core.dao;

import com.mahara.stocker.core.model.SysRole;
import org.springframework.data.repository.CrudRepository;

public interface SysRoleRepository extends CrudRepository<SysRole, Long>, SysRoleJdbc {}
