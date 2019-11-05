package com.softwareag.apilyzer.model;

import java.util.ArrayList;
import java.util.List;

public class SubCategory {

  private String name;

  private List<Issue> issues = new ArrayList<>();

  public SubCategory() {

  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Issue> getIssues() {
    return issues;
  }

  public void setIssues(List<Issue> issues) {
    this.issues = issues;
  }
}
