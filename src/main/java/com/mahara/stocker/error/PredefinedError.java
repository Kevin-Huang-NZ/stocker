package com.mahara.stocker.error;

public enum PredefinedError implements CommonError {
  BAD_REQUEST(900400, "非法请求。"),
  UNAUTHORIZED(900401, "登录失败。"),
  FORBIDDEN(900403, "拒绝访问。"),
  DATA_NOT_EXIST(900404, "数据不存在。"),
  DATA_CONFLICT(900409, "数据冲突。"),
  UNKNOWN_ERROR(999999, "未知错误。");

  private PredefinedError(int errorCode, String errorMessage) {
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
  }
  ;

  private int errorCode;
  private String errorMessage;

  @Override
  public int getErrorCode() {
    return this.errorCode;
  }

  @Override
  public String getErrorMessage() {
    return this.errorMessage;
  }
}
