package com.softwareag.apilyzer.model;

import java.util.ArrayList;
import java.util.List;

public class SubCategory {

  private String name;

  private List<String> issues = new ArrayList<>();

  public SubCategory() {

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getIssues() {
    return issues;
  }

  public void setIssues(List<String> issues) {
    this.issues = issues;
  }
}
