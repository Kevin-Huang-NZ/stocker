package com.mahara.stocker.error;

public class CustomizedException extends Exception implements CommonError {
  private static final long serialVersionUID = 1L;
  private CommonError commonError;
  private String specificMessage = "";

  public CustomizedException(CommonError commonError) {
    super();
    this.commonError = commonError;
  }

  public CustomizedException(CommonError commonError, String errorMessage) {
    super();
    this.commonError = commonError;
    this.specificMessage = errorMessage;
  }

  @Override
  public int getErrorCode() {
    return this.commonError.getErrorCode();
  }

  @Override
  public String getErrorMessage() {
    if ("".equals(this.specificMessage)) {
      return this.commonError.getErrorMessage();
    } else {
      return this.specificMessage;
    }
  }
}
