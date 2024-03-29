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

public class SecuritySchemeRule extends AbstractRuleSpecification {

  @Override
  public String getRuleName() {
    return RuleEnum.HTTP_SECURITY_SCHEME.getRulename();
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
    return RuleEnum.HTTP_SECURITY_SCHEME.getSummary();
  }

  @Override
  public String getDescription() {
    return RuleEnum.HTTP_SECURITY_SCHEME.getDescription();
  }

  @Override
  public String getRemedy() {
    return RuleEnum.HTTP_SECURITY_SCHEME.getRemedy();
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
      Issue issue = createIssue(buildContext(server));
      issue.setErrorInfo("Server with url: " + server.getUrl());
      issues.add(issue);
    }
  }

  private Map<String, String> buildContext(Server server) {
    Map<String, String> context = new HashMap<>();
    context.put("rulename", RuleEnum.HTTP_SECURITY_SCHEME.name());
    context.put("rulepath", server.getUrl());
    return context;
  }

  @Override
  public List<Issue> getIssues() {
    return issues;
  }


}
