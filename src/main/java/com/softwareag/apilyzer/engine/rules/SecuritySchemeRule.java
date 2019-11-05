package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.*;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SecuritySchemeRule implements IRuleSpecification {
  private List<Issue> issues = Collections.emptyList();
  private int totalCount = 0;
  private int successCount = 0;

  @Override
  public String getRuleName() {
    return RuleEnum.SECURITY_SCHEME.getRulename();
  }

  @Override
  public CategoryEnum getCategoryName() {
    return CategoryEnum.SECURITY_STANDARDS;
  }

  @Override
  public SubCategoryEnum getSubCategoryName() {
    return SubCategoryEnum.SECURITY_SCHEMES;
  }

  @Override
  public SeverityEnum getSeverity() {
    return SeverityEnum.HIGH;
  }

  @Override
  public String getSummary() {
    return RuleEnum.SECURITY_SCHEME.getSummary();
  }

  @Override
  public String getDescription() {
    return RuleEnum.SECURITY_SCHEME.getDescription();
  }

  @Override
  public String getRemedy() {
    return RuleEnum.SECURITY_SCHEME.getRemedy();
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
      issues.add(createIssue(buildContext(server)));
    }
  }

  private Function<String, String> getScheme = (url) -> {
    return url.split(":")[0];
  };

  private Map<String, String> buildContext(Server server) {
    Map<String, String> context = new HashMap<>();
    context.put("rulename", RuleEnum.SECURITY_SCHEME.name());
    context.put("rulepath", server.getUrl());
    return context;
  }

  private Issue createIssue(Map<String, String> context) {
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

  @Override
  public List<Issue> getIssues() {
    return null;
  }

  @Override
  public int getTotalCount() {
    return totalCount;
  }

  @Override
  public int getSuccessCount() {
    return successCount;
  }
}
