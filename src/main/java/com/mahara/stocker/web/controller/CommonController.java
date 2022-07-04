package com.mahara.stocker.web.controller;

import com.mahara.stocker.error.PredefinedError;
import com.mahara.stocker.web.response.Root;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommonController {

  @Operation(summary = "返回401错误")
  @GetMapping("/401")
  public Root unauthorized() {
    return Root.create(PredefinedError.UNAUTHORIZED);
  }

  @Operation(summary = "返回403错误")
  @GetMapping("/403")
  public Root forbidden() {
    return Root.create(PredefinedError.FORBIDDEN);
  }

  @Operation(summary = "返回404错误")
  @GetMapping("/404")
  public Root dataNotFound() {
    return Root.create(PredefinedError.DATA_NOT_EXIST);
  }
}
