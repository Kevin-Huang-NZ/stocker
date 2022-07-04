package com.mahara.stocker.error;

import com.mahara.stocker.web.response.ErrorField;
import com.mahara.stocker.web.response.ErrorInfo;
import com.mahara.stocker.web.response.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

  @ExceptionHandler({BindException.class})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Root handleBindException(BindException ex) {
    ErrorInfo errorInfo = new ErrorInfo();
    errorInfo.setErrorCode(PredefinedError.BAD_REQUEST.getErrorCode());
    errorInfo.setErrorMessage("校验失败。");

    BindingResult bindingResult = ex.getBindingResult();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      errorInfo.addErrorField(
          new ErrorField(fieldError.getField(), fieldError.getDefaultMessage()));
    }
    return Root.create(Root.RESPONSE_STATUS_ERROR, errorInfo);
  }

  @ExceptionHandler({ConstraintViolationException.class})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Root handleConstraintViolationException(ConstraintViolationException ex) {
    ErrorInfo errorInfo = new ErrorInfo();
    errorInfo.setErrorCode(PredefinedError.BAD_REQUEST.getErrorCode());
    errorInfo.setErrorMessage("校验失败。");
    var constraintViolations = ex.getConstraintViolations();
    for (var oneError : constraintViolations) {
      errorInfo.addErrorField(
          new ErrorField(oneError.getPropertyPath().toString(), oneError.getMessage()));
      log.debug("field: {}, value: {}, message: {}", oneError.getPropertyPath().toString(), oneError.getInvalidValue(), oneError.getMessage());
    }
    return Root.create(Root.RESPONSE_STATUS_ERROR, errorInfo);
  }

  @ExceptionHandler({AccessDeniedException.class})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Root handleAuthorizationException(AccessDeniedException ex) {
    ErrorInfo errorInfo = new ErrorInfo(PredefinedError.FORBIDDEN);
    return Root.create(Root.RESPONSE_STATUS_ERROR, errorInfo);
  }
  //    @ExceptionHandler({AuthenticationException.class})
  //    @ResponseStatus(HttpStatus.OK)
  //    @ResponseBody
  //    public Root handleAuthenticationException(AuthenticationException ex) {
  //        ErrorInfo errorInfo = new ErrorInfo(PredefinedError.UNAUTHORIZED);
  //        return Root.create(Root.RESPONSE_STATUS_ERROR, errorInfo);
  //    }
  //
  //    @ExceptionHandler({AuthorizationException.class})
  //    @ResponseStatus(HttpStatus.OK)
  //    @ResponseBody
  //    public Root handleAuthorizationException(AuthorizationException ex) {
  //        ErrorInfo errorInfo = new ErrorInfo(PredefinedError.FORBIDDEN);
  //        return Root.create(Root.RESPONSE_STATUS_ERROR, errorInfo);
  //    }

  @ExceptionHandler({CustomizedException.class})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Root handleCustomizedException(CustomizedException ex) {
    ErrorInfo errorInfo = new ErrorInfo(ex);
    return Root.create(Root.RESPONSE_STATUS_ERROR, errorInfo);
  }

  @ExceptionHandler({Exception.class})
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Root handleException(Exception ex) {
    ErrorInfo errorInfo = new ErrorInfo(PredefinedError.UNKNOWN_ERROR);
    return Root.create(Root.RESPONSE_STATUS_ERROR, errorInfo);
  }

  //    @ExceptionHandler({ConstraintViolationException.class})
  //    @ResponseStatus(HttpStatus.OK)
  //    @ResponseBody
  //    public Root handleConstraintViolationException(ConstraintViolationException ex) {
  //        ErrorInfo errorInfo = new ErrorInfo(PredefinedError.DATA_CONFLICT);
  //        errorInfo.setErrorMessage(ex.getMessage());
  //        return Root.create(Root.RESPONSE_STATUS_ERROR, errorInfo);
  //    }
}
