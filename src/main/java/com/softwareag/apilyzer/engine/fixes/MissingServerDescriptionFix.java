package com.softwareag.apilyzer.engine.fixes;

import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

import java.util.Map;
import java.util.Optional;

public class MissingServerDescriptionFix extends MissingDescriptionRuleFix {

  @Override
  public OpenAPI fixIssue(Issue issue, OpenAPI openAPI, Object data) {
    Map<String, String> context = issue.getContext();
    Optional<Server> serverOptional = openAPI.getServers().stream().filter(server -> server.getUrl().equals(context.get("rulepath"))).findAny();
    serverOptional.get().setDescription(String.valueOf(data));
    return openAPI;
  }
}
