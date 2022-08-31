package com.lunatech.imdb.apiresponse;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiDataResponse<T> {
  private HttpStatus status;
  private String message;
  private T data;

  public ApiDataResponse(HttpStatus status) {
    this.status = status;
  }
}