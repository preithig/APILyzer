package com.softwareag.apilyzer.model;

public class FixData {

  private String description;
  private String url;
  private String responseStatusCode;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getResponseStatusCode() {
    return responseStatusCode;
  }

  public void setResponseStatusCode(String responseStatusCode) {
    this.responseStatusCode = responseStatusCode;
  }
}
