package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.RuleEnum;
import com.softwareag.apilyzer.engine.IssuesUtil;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class MissingServerDescriptionRule extends MissingDescriptionRule {

  private IssuesUtil issuesUtil;

  @Autowired
  public void setIssuesUtil(IssuesUtil issuesUtil) {
    this.issuesUtil = issuesUtil;
  }

  public void executeRule(OpenAPI api) {
    if (Objects.nonNull(api.getServers())) {
      totalCount = api.getServers().size();
      List<Server> serverWithMissingDesc = api.getServers().stream().filter(server -> Strings.isNullOrEmpty(server.getDescription())).collect(Collectors.toList());
      successCount = totalCount - serverWithMissingDesc.size();
      for (Server server : serverWithMissingDesc) {
        issues.add(issuesUtil.createIssue(this, buildContext(server)));
      }
    }
  }

  private Map<String, String> buildContext(Server server) {
    Map<String, String> context = new HashMap<>();
    context.put("rulename", RuleEnum.MISSING_SERVER_DESC.name());
    context.put("rulepath", server.getUrl());
    return context;
  }

  @Override
  public String getRuleName() {
    return RuleEnum.MISSING_SERVER_DESC.getRulename();
  }

  @Override
  public java.lang.String getSummary() {
    return RuleEnum.MISSING_SERVER_DESC.getSummary();
  }

  @Override
  public String getDescription() {
    return RuleEnum.MISSING_SERVER_DESC.getDescription();
  }

  @Override
  public String getRemedy() {
    return RuleEnum.MISSING_SERVER_DESC.getRemedy();
  }

  @Override
  public String getErrorInfo() {
    return null;
  }


}
