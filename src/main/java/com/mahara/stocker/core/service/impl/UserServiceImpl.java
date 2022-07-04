package com.mahara.stocker.core.service.impl;

import com.mahara.stocker.core.dao.SysPermissionRepository;
import com.mahara.stocker.core.dao.SysRoleRepository;
import com.mahara.stocker.core.dao.UserRepository;
import com.mahara.stocker.core.dao.UserRoleRepository;
import com.mahara.stocker.core.model.SysPermission;
import com.mahara.stocker.core.model.SysRole;
import com.mahara.stocker.core.model.User;
import com.mahara.stocker.core.model.UserRole;
import com.mahara.stocker.core.service.UserService;
import com.mahara.stocker.error.CustomizedException;
import com.mahara.stocker.error.PredefinedError;
import com.mahara.stocker.security.PasswordEncoderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
  @Autowired private UserRoleRepository repository;
  @Autowired private UserRepository userRepository;
  @Autowired private SysRoleRepository roleRepository;
  @Autowired private SysPermissionRepository permissionRepository;
  @Autowired
  private PasswordEncoderUtil passwordEncoderUtil;

  @Override
  public void addRole(UserRole entity) {
    repository.save(entity);
  }

  @Override
  public void removeRole(UserRole entity) {
    repository.deleteByUniqueKey(entity.getUserId(), entity.getRoleId());
  }

  @Transactional
  @Override
  public void deleteUser(Long id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
      repository.deleteByUser(id);
    }
  }

  @Override
  public Optional<User> findByUniqueKey(String phone) {
    return userRepository.findByUniqueKey(phone, null);
  }

  //    @Override
  //    public Optional<User> checkPhone(String phone, Long id) {
  //        return userRepository.findByUniqueKey(phone, id);
  //    }

  @Override
  public List<SysRole> getUserRoles(Long userId) {
    return roleRepository.getByUserId(userId);
  }

  @Override
  public List<SysPermission> getUserPermissions(List<Long> roleIds) {
    return permissionRepository.getByRoleIds(roleIds);
  }

  @Override
  public User createUser(User entity) throws CustomizedException {
    var checkExist = userRepository.findByUniqueKey(entity.getPhone(), 0l);
    if (checkExist.isPresent()) {
      throw new CustomizedException(PredefinedError.DATA_NOT_EXIST, "手机号重复，保存失败。");
    } else {
      log.debug("User password before encoding: {}", entity.getPassword());
      var encodedPassword = passwordEncoderUtil.getPasswordEncoder().encode(entity.getPassword());
      entity.setPassword(encodedPassword);
      log.debug("User password after encoding: {}", entity.getPassword());
      return userRepository.save(entity);
    }
  }

  @Override
  public User updateUser(User entity) throws CustomizedException {
    var checkExist = userRepository.findByUniqueKey(entity.getPhone(), entity.getId());
    if (checkExist.isPresent()) {
      throw new CustomizedException(PredefinedError.DATA_NOT_EXIST, "手机号重复，保存失败。");
    } else {
      var encodedPassword = passwordEncoderUtil.getPasswordEncoder().encode(entity.getPassword());
      entity.setPassword(encodedPassword);
      return userRepository.save(entity);
    }
  }
}
