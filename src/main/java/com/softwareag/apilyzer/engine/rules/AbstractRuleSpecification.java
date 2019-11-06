package com.softwareag.apilyzer.engine.rules;

import com.google.common.collect.Lists;
import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.model.Issue;

import java.util.List;
import java.util.Map;

public abstract class AbstractRuleSpecification implements IRuleSpecification {
  List<String> issues = Lists.newArrayList();
  int totalCount = 0;
  int successCount = 0;

  @Override
  public int getTotalCount() {
    return totalCount;
  }

  @Override
  public int getSuccessCount() {
    return successCount;
  }


  @Override
  public List<String> getIssues() {
    return issues;
  }

  protected Issue createIssue(Map<String, String> context) {
    Issue issue = new Issue();
    issue.setDescription(getDescription());
    issue.setErrorInfo(getErrorInfo());
    issue.setName(getRuleName());
    issue.setRemedy(getRemedy());
    issue.setSeverity(getSeverity().name());
    issue.setSummary(getSummary());
    issue.setContext(context);
    return issue;
  }

}
