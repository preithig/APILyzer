package com.softwareag.apilyzer.model;

import com.softwareag.apilyzer.api.RuleEnum;

public class RulesConfiguration {

  private RuleEnum ruleName;

  private boolean enabled;

  public RuleEnum getRuleName() {
    return ruleName;
  }

  public void setRuleName(RuleEnum ruleName) {
    this.ruleName = ruleName;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
