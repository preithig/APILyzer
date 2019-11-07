package com.softwareag.apilyzer.exception;

public class NotValidAPIException extends Exception {
  public NotValidAPIException(String this_openAPI_is_invalid) {
    super(this_openAPI_is_invalid);
  }
}
