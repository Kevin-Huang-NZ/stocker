package com.mahara.stocker.web.controller;

import com.mahara.stocker.GlobalConst;
import com.mahara.stocker.core.dao.UserRepository;
import com.mahara.stocker.core.model.User;
import com.mahara.stocker.core.model.UserRole;
import com.mahara.stocker.core.service.UserService;
import com.mahara.stocker.error.CustomizedException;
import com.mahara.stocker.error.PredefinedError;
import com.mahara.stocker.web.request.PaginationIn;
import com.mahara.stocker.web.response.Root;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired private UserRepository userRepository;
  @Autowired private UserService userService;

  @Operation(summary = "分页查询用户")
  @SecurityRequirement(name = GlobalConst.SECURITY_SCHEMES_KEY)
  @PreAuthorize("hasAnyAuthority('user:*', 'user:retrieve')")
  @GetMapping("/")
  public Root search(
      @Parameter(name = "paginationIn", description = "分页信息，包含页码(number)和页大小(size)") @Validated
          PaginationIn paginationIn,
      @Parameter(name = "keyword", description = "查询关键字") @RequestParam(required = false)
          String keyword) {
    var searchResult = userRepository.search(keyword, paginationIn);
    return Root.create(searchResult);
  }

  @Operation(summary = "使用ID查询用户")
  @SecurityRequirement(name = GlobalConst.SECURITY_SCHEMES_KEY)
  @PreAuthorize("hasAnyAuthority('user:*', 'user:retrieve')")
  @GetMapping("/{id}")
  public Root findOne(
      @Parameter(name = "id", description = "用户的ID") @PathVariable @Min(0l) Long id) {
    var bean = userRepository.findById(id);
    if (bean.isPresent()) {
      return Root.create(bean.get());
    } else {
      return Root.create(PredefinedError.DATA_NOT_EXIST);
    }
  }

  @Operation(summary = "新建用户")
  @SecurityRequirement(name = GlobalConst.SECURITY_SCHEMES_KEY)
  @PreAuthorize("hasAnyAuthority('user:*', 'user:create')")
  @PostMapping("/create")
  public Root create(@RequestBody @Validated(value = User.Create.class) User entity)
      throws CustomizedException {
    return Root.create(userService.createUser(entity));
  }

  @Operation(summary = "更新用户")
  @SecurityRequirement(name = GlobalConst.SECURITY_SCHEMES_KEY)
  @PreAuthorize("hasAnyAuthority('user:*', 'user:update')")
  @PostMapping("/update")
  public Root update(@RequestBody @Validated(value = User.Update.class) User entity)
      throws CustomizedException {
    return Root.create(userService.updateUser(entity));
  }

  @Operation(summary = "使用ID删除用户")
  @SecurityRequirement(name = GlobalConst.SECURITY_SCHEMES_KEY)
  @PreAuthorize("hasAnyAuthority('user:*', 'user:delete')")
  @PostMapping("/{id}/del")
  public Root delete(
      @Parameter(name = "id", description = "用户的ID") @PathVariable @Min(0l) Long id) {
    userService.deleteUser(id);
    return Root.create();
  }

  @Operation(summary = "为用户增加某个角色")
  @SecurityRequirement(name = GlobalConst.SECURITY_SCHEMES_KEY)
  @PreAuthorize("hasAnyAuthority('user:*', 'user:add-role')")
  @PostMapping("/{id}/add-role/{roleId}")
  public Root addRole(
      @Parameter(name = "id", description = "用户的ID") @PathVariable @Min(0l) Long id,
      @Parameter(name = "roleId", description = "角色的ID") @PathVariable @Min(0l) Long roleId) {
    userService.addRole(new UserRole(id, roleId));
    return Root.create();
  }

  @Operation(summary = "删除用户的某个角色")
  @SecurityRequirement(name = GlobalConst.SECURITY_SCHEMES_KEY)
  @PreAuthorize("hasAnyAuthority('user:*', 'user:remove-role')")
  @PostMapping("/{id}/remove-role/{roleId}")
  public Root removeRole(
      @Parameter(name = "id", description = "用户的ID") @PathVariable @Min(0l) Long id,
      @Parameter(name = "roleId", description = "角色的ID") @PathVariable @Min(0l) Long roleId) {
    userService.removeRole(new UserRole(id, roleId));
    return Root.create();
  }
}
