package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.RuleEnum;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.elasticsearch.common.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MissingServerDescriptionRule extends MissingDescriptionRule {


  public void execute(OpenAPI api) {
    if (Objects.nonNull(api.getServers())) {

      List<Server> validServerURLs = api.getServers().stream().filter(server -> !server.getUrl().equals("/")).collect(Collectors.toList());

      totalCount = validServerURLs.size();
      List<Server> serverWithMissingDesc = validServerURLs.stream().filter(server -> Strings.isNullOrEmpty(server.getDescription())).collect(Collectors.toList());
      successCount = totalCount - serverWithMissingDesc.size();
      for (Server server : serverWithMissingDesc) {
        //issues.add(issuesUtil.createIssue(this, buildContext(server)));
        issues.add(createIssue(buildContext(server)));
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
