package com.softwareag.apilyzer.model;

import com.softwareag.apilyzer.api.RuleEnum;

public class RulesConfiguration {

  private RuleEnum ruleName;

  private boolean status;

  public RuleEnum getRuleName() {
    return ruleName;
  }

  public void setRuleName(RuleEnum ruleName) {
    this.ruleName = ruleName;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }
}
