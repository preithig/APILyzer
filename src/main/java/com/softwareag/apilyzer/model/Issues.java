package org.softwareag.techinterrupt.apilyzer.model;

public class Issues {

  private String summary;

  private String description;

  private String remedy;

  private String severity;

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
}
