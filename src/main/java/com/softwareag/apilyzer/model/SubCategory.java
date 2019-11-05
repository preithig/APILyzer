package org.softwareag.techinterrupt.apilyzer.model;

public class SubCategory {

  private String name;

  private Issues[] issues;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Issues[] getIssues() {
    return issues;
  }

  public void setIssues(Issues[] issues) {
    this.issues = issues;
  }
}
