package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.RuleEnum;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.api.SubCategoryEnum;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Missing2xxResponseRule extends AbstractRuleSpecification {

  @Override
  public String getRuleName() {
    return RuleEnum.RESPONSE_DETAILS.getRulename();
  }

  @Override
  public CategoryEnum getCategoryName() {
    return CategoryEnum.API_STANDARDS;
  }

  @Override
  public SubCategoryEnum getSubCategoryName() {
    return SubCategoryEnum.RESPONSE_DETAILS;
  }

  @Override
  public SeverityEnum getSeverity() {
    return SeverityEnum.MEDIUM;
  }

  @Override
  public String getSummary() {
    return RuleEnum.RESPONSE_DETAILS.getSummary();
  }

  @Override
  public String getDescription() {
    return RuleEnum.RESPONSE_DETAILS.getDescription();
  }

  @Override
  public String getRemedy() {
    return RuleEnum.RESPONSE_DETAILS.getRemedy();
  }

  @Override
  public String getErrorInfo() {
    return null;
  }

  @Override
  public void execute(OpenAPI api) {
    List<Server> servers = api.getServers();
    totalCount = servers.size();
    List<Server> httpServers = servers.stream().filter(server -> server.getUrl().split(":")[0].equals("http")).collect(Collectors.toList());
    successCount = totalCount - httpServers.size();
    for (Server server : httpServers) {
      //issues.add(issuesUtil.createIssue(this, buildContext(server)));
      issues.add(createIssue(buildContext(server)));
    }
  }

  private Map<String, String> buildContext(Server server) {
    Map<String, String> context = new HashMap<>();
    context.put("rulename", RuleEnum.SECURITY_SCHEME.name());
    context.put("rulepath", server.getUrl());
    return context;
  }

  @Override
  public List<Issue> getIssues() {
    return issues;
  }


}
