package com.mahara.stocker.web.response;

import com.mahara.stocker.error.CustomizedException;
import com.mahara.stocker.error.PredefinedError;

import java.util.ArrayList;
import java.util.List;

public class ErrorInfo {
  private int errorCode;
  private String errorMessage;
  private List<ErrorField> errorFields = new ArrayList<>();

  public ErrorInfo() {}

  public ErrorInfo(PredefinedError predefinedError) {
    this.errorCode = predefinedError.getErrorCode();
    this.errorMessage = predefinedError.getErrorMessage();
  }

  public ErrorInfo(CustomizedException customizedException) {
    this.errorCode = customizedException.getErrorCode();
    this.errorMessage = customizedException.getErrorMessage();
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public List<ErrorField> getErrorFields() {
    return errorFields;
  }

  public void setErrorFields(List<ErrorField> errorFields) {
    if (errorFields == null) {
      this.errorFields = new ArrayList<>();
    } else {
      this.errorFields = errorFields;
    }
  }

  public void addErrorField(ErrorField errorField) {
    this.errorFields.add(errorField);
  }
}
