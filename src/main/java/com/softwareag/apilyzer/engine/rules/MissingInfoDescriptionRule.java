package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.RuleEnum;
import com.softwareag.apilyzer.engine.IssuesUtil;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MissingInfoDescriptionRule extends MissingDescriptionRule {

  private IssuesUtil issuesUtil;

  @Autowired
  public void setIssuesUtil(IssuesUtil issuesUtil) {
    this.issuesUtil = issuesUtil;
  }

  public void executeRule(OpenAPI api) {
    Info info = api.getInfo();
    if (Strings.isNullOrEmpty(info.getDescription())) {
      issues.add(issuesUtil.createIssue(this, buildContext()));
      totalCount = 0;
    } else {
      totalCount = 1;
      successCount = 1;
    }
  }

  private Map<String, String> buildContext() {

    Map<String, String> context = new HashMap<>();
    context.put("rulename", RuleEnum.MISSING_INFO_DESC.name());
    return context;
  }


  @Override
  public String getRuleName() {
    return RuleEnum.MISSING_INFO_DESC.getRulename();
  }

  @Override
  public java.lang.String getSummary() {
    return RuleEnum.MISSING_INFO_DESC.getSummary();
  }

  @Override
  public String getDescription() {
    return RuleEnum.MISSING_INFO_DESC.getDescription();
  }

  @Override
  public String getRemedy() {
    return RuleEnum.MISSING_INFO_DESC.getRemedy();
  }

  @Override
  public String getErrorInfo() {
    return null;
  }


}
