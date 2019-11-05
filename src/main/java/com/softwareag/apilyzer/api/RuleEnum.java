package com.softwareag.apilyzer.api;

public enum RuleEnum {
  MISSING_INFO_DESC("MISSING_INFO_DESC", "Description is missing in the Info section", "Description is missing in the Info section", "To fix this issue, please give a value to the  description field in the Info section"),
  MISSING_SERVER_DESC("MISSING_SERVER_DESC", "Description is missing in the Server section", "Description is missing in the Server section", "To fix this issue, please give a value to the  description field in the Server section"),
  SECURITY_SCHEME("HTTP_SECURITY_SCHEME","API accepts HTTP requests in the clear","The API accepts HTTP communications in the clear. HTTP traffic is not encrypted and can thus be easily intercepted.","Remove http from the schemes list, and only include https");

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
