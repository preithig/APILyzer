package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.RuleEnum;
import com.softwareag.apilyzer.engine.fixes.*;
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
      case HTTP_SECURITY_SCHEME:
        return new SecuritySchemeRuleFix().fix(issue, openAPI, data);
      case OPERATION_2XX_RESPONSE:
        return new Missing2xxResponseRuleFix().fix(issue, openAPI, data);
      case MISSING_SERVER_INFORMATION:
        return new MissingServerInformationFix().fix(issue, openAPI, data);
      case MISSING_RESPONSE_EXAMPLE:
        return new MissingResponseExampleFix().fix(issue, openAPI, data);
      case MISSING_REQUEST_BODY_EXAMPLE:
        return new MissingRequestBodyExampleFix().fix(issue, openAPI, data);
    }
    return openAPI;
  }

}
