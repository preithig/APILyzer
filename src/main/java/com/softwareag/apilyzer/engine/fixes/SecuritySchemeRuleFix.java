package com.softwareag.apilyzer.engine.fixes;

import com.softwareag.apilyzer.api.IRuleFix;
import com.softwareag.apilyzer.model.FixData;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class SecuritySchemeRuleFix implements IRuleFix {

  @Override
  public OpenAPI fix(Issue issue, OpenAPI openAPI, FixData data) {

    Map<String, String> context = issue.getContext();
    List<Server> httpServers = openAPI.getServers().stream().filter(server -> server.getUrl().split(":")[0].equals("http")).collect(Collectors.toList());
    Optional<Server> optional = httpServers.stream().filter(server -> server.getUrl().equals(context.get("rulepath"))).findAny();
    optional.get().setUrl(data.getUrl());
    return openAPI;
  }
}
