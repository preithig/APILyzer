package com.softwareag.apilyzer.model;

import java.util.List;

public class SubCategory {

  private String name;

  private List<Issues> issues;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Issues> getIssues() {
    return issues;
  }

  public void setIssues(List<Issues> issues) {
    this.issues = issues;
  }
}
