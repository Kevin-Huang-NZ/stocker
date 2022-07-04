package com.mahara.stocker.web.response;

import com.mahara.stocker.error.PredefinedError;

public class Root {
  public static final String RESPONSE_STATUS_SUCCESS = "success";
  public static final String RESPONSE_STATUS_ERROR = "error";
  /** success / error */
  private String status;
  /** status是success，data返回前端需要的json status是error，data返回通用错误信息 */
  private Object data;

  public static Root create() {
    return Root.create(Root.RESPONSE_STATUS_SUCCESS, null);
  }

  public static Root create(Object data) {
    return Root.create(Root.RESPONSE_STATUS_SUCCESS, data);
  }

  public static Root create(PredefinedError predefinedError) {
    return Root.create(Root.RESPONSE_STATUS_ERROR, new ErrorInfo(predefinedError));
  }

  public static Root create(String status, Object data) {
    Root type = new Root();
    type.setData(data);
    type.setStatus(status);
    return type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }
}
