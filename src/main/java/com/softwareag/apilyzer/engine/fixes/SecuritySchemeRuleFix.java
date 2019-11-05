package com.softwareag.apilyzer.engine.fixes;

import com.softwareag.apilyzer.api.IRuleFix;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;

public class SecuritySchemeRuleFix implements IRuleFix {

  @Override
  public boolean fix(Issue issue, OpenAPI openAPI, Object data) {
    return false;
  }
}
