package com.softwareag.apilyzer.engine.fixes;

import com.softwareag.apilyzer.api.IRuleFix;
import com.softwareag.apilyzer.model.FixData;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

import java.util.Map;
import java.util.Optional;

public class MissingServerDescriptionFix implements IRuleFix {

  @Override
  public OpenAPI fix(Issue issue, OpenAPI openAPI, FixData data) {
    Map<String, String> context = issue.getContext();
    Optional<Server> serverOptional = openAPI.getServers().stream().filter(server -> server.getUrl().equals(context.get("rulepath"))).findAny();
    serverOptional.get().setDescription(data.getDescription());
    return openAPI;
  }
}
