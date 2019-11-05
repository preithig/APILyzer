package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.elasticsearch.common.Strings;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MissingServerDescriptionRule extends MissingDescriptionRule {
  public Issue executeRule(OpenAPI api) {
    if (Objects.nonNull(api.getServers())) {
      List<Server> serverWithMissingDesc = api.getServers().stream().filter(server -> Strings.isNullOrEmpty(server.getDescription())).collect(Collectors.toList());

    }
    return null;
  }
}
