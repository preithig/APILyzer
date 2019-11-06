package com.softwareag.apilyzer.engine.fixes;

import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;

public class MissingInfoDescriptionFix extends MissingDescriptionRuleFix {

  @Override
  public OpenAPI fixIssue(Issue issue, OpenAPI openAPI, Object data) {
    openAPI.getInfo().setDescription(String.valueOf(data));
    return openAPI;
  }
}
