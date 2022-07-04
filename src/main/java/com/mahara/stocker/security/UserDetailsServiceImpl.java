package com.mahara.stocker.security;

import com.mahara.stocker.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * 实现UserDetailsService接口。
 * 自定义用户信息查询
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
    var result = userService.findByUniqueKey(phone);
    if (result.isEmpty()) {
      throw new UsernameNotFoundException("用户不存在。");
    }
    var user = result.get();
    var roles = userService.getUserRoles(user.getId());
    var roleIds = roles.stream().map(r -> r.getId()).collect(Collectors.toList());
    var permissions = userService.getUserPermissions(roleIds);
    var permissionList = permissions.stream().map(p -> p.getPermission()).collect(Collectors.toList());
    return new SecurityUser(user, permissionList);
  }
}
