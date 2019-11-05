package com.softwareag.apilyzer.api;

public enum RuleEnum {
  MISSING_INFO_DESC("MISSING_INFO_DESC", "Description is missing in the Info section", "Description is missing in the Info section", "To fix this issue, please give a value to the  description field in the Info section");

  private String rulename, summary, description, remedy;


  RuleEnum(String rulename, String summary, String description, String remedy) {
    this.rulename = rulename;
    this.summary = summary;
    this.description = description;
    this.remedy = remedy;
  }

  public String getRulename() {
    return rulename;
  }

  public String getSummary() {
    return summary;
  }

  public String getDescription() {
    return description;
  }

  public String getRemedy() {
    return remedy;
  }


  public static RuleEnum get(String rulename) {
    for (RuleEnum ruleEnum : RuleEnum.values()) {
      if (ruleEnum.getRulename().equals(rulename)) {
        return ruleEnum;
      }
    }
    return null;
  }


}
