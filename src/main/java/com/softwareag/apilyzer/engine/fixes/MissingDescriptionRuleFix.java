package com.softwareag.apilyzer.engine.fixes;

import com.softwareag.apilyzer.api.IRuleFix;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Paths;

import java.util.Map;
import java.util.Set;

public class MissingDescriptionRuleFix implements IRuleFix {

  @Override
  public boolean fix(Issue issue, OpenAPI openAPI, Object data) {

    Map<String, String> context = issue.getContext();
    Set<String> keys = context.keySet();
    Paths paths = openAPI.getPaths();
    for (String key : keys) {
      paths.get(key);

    }
    return false;
  }
}
