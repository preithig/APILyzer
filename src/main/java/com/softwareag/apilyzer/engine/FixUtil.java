package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.RuleEnum;
import com.softwareag.apilyzer.engine.fixes.MissingInfoDescriptionFix;
import com.softwareag.apilyzer.engine.fixes.MissingServerDescriptionFix;
import com.softwareag.apilyzer.engine.fixes.SecuritySchemeRuleFix;
import com.softwareag.apilyzer.model.FixData;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;

public class FixUtil {

  public static OpenAPI fix(Issue issue, OpenAPI openAPI, FixData data) {
    switch (RuleEnum.valueOf(issue.getName())) {
      case MISSING_INFO_DESC:
        return new MissingInfoDescriptionFix().fix(issue, openAPI, data);
      case MISSING_SERVER_DESC:
        return new MissingServerDescriptionFix().fix(issue, openAPI, data);
      case SECURITY_SCHEME:
        return new SecuritySchemeRuleFix().fix(issue, openAPI, data);
    }
    return openAPI;
  }

}
