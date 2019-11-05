package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.elasticsearch.common.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MissingServerDescriptionRule extends MissingDescriptionRule {
  public List<Issue> executeRule(OpenAPI api) {
    if (Objects.nonNull(api.getServers())) {
      List<Issue> issues = new ArrayList<>();
      List<Server> serverWithMissingDesc = api.getServers().stream().filter(server -> Strings.isNullOrEmpty(server.getDescription())).collect(Collectors.toList());
      for (Server server : serverWithMissingDesc) {
        issues.add(createIssue(buildContext(api)));
      }
    }
    return null;
  }

  private Map<String, String> buildContext(OpenAPI api) {

    return null;
  }

  @Override
  public String getRuleName() {
    return "Missing Description in Server";
  }

  @Override
  public java.lang.String getSummary() {
    return null;
  }

  @Override
  public String getDescription() {
    return null;
  }

  @Override
  public String getRemedy() {
    return null;
  }

  @Override
  public String getErrorInfo() {
    return null;
  }


}
