package com.softwareag.apilyzer.engine.fixes;

import com.softwareag.apilyzer.api.IRuleFix;
import com.softwareag.apilyzer.api.RuleEnum;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;

public abstract class MissingDescriptionRuleFix implements IRuleFix {

  public abstract OpenAPI fixIssue(Issue issue, OpenAPI openAPI, Object data);

  @Override
  public OpenAPI fix(Issue issue, OpenAPI openAPI, Object data) {
    switch (RuleEnum.valueOf(issue.getName())) {
      case MISSING_INFO_DESC:
        return new MissingInfoDescriptionFix().fixIssue(issue, openAPI, data);
      case MISSING_SERVER_DESC:
        return new MissingServerDescriptionFix().fixIssue(issue, openAPI, data);
    }
    return openAPI;
  }

}