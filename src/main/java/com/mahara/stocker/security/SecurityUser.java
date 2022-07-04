package com.mahara.stocker.security;

import com.alibaba.druid.util.StringUtils;
import com.mahara.stocker.core.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 实现UserDetails接口，替换Spring Security提供的默认实现User
 */
public class SecurityUser implements UserDetails {
  private User user;
  private List<String> permissions;

  public SecurityUser(User user, List<String> permissions) {
    this.user = user;
    this.permissions = permissions;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (this.permissions == null) {
      return null;
    }
    return permissions.stream().map(p -> (new SimpleGrantedAuthority(p))).collect(Collectors.toList());
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public String getUsername() {
    return this.user.getPhone();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return StringUtils.equals(this.user.getStatus(), "1");
  }
}
