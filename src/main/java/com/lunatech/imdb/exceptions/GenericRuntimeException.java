package com.lunatech.imdb.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericRuntimeException extends RuntimeException {
  private String errorCode;
  private String errorMessage;

  public GenericRuntimeException(String errorCode, String errorMessage) {
    this.setErrorCode(errorCode);
    this.setErrorMessage(errorMessage);
  }
  public GenericRuntimeException(String errorMessage, Throwable exception) {
    super(exception);
    this.setErrorCode("");
    this.setErrorMessage(errorMessage);
  }
}
