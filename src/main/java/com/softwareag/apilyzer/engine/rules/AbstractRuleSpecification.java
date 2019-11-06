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

}
