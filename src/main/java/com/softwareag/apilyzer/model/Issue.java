package com.softwareag.apilyzer.model;

import java.util.Map;

public class Issue {

  private String id;

  private String name;

  private String summary;

  private String description;

  private String remedy;

  private String severity;

  private String errorInfo;

  /**
   *
   */
  private Map<String, String> context;

  public Issue() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getErrorInfo() {
    return errorInfo;
  }

  public void setErrorInfo(String errorInfo) {
    this.errorInfo = errorInfo;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getRemedy() {
    return remedy;
  }

  public void setRemedy(String remedy) {
    this.remedy = remedy;
  }

  public String getSeverity() {
    return severity;
  }

  public void setSeverity(String severity) {
    this.severity = severity;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Map<String, String> getContext() {
    return context;
  }

  public void setContext(Map<String, String> context) {
    this.context = context;
  }
}
