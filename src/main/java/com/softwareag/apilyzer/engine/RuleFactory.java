package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.engine.rules.*;

public class RuleFactory {

  public IRuleSpecification resolveRule(String ruleName) {
    switch (ruleName) {
      case "MISSING_INFO_DESC":
        return new MissingInfoDescriptionRule();
      case "MISSING_SERVER_DESC":
        return new MissingServerDescriptionRule();
      case "HTTP_SECURITY_SCHEME":
        return new SecuritySchemeRule();
      case "MISSING_SERVER_INFORMATION":
        return new MissingServerInformationRule();
      case "OPERATION_2XX_RESPONSE":
        return new Missing2xxResponseRule();
      case "MISSING_REQUEST_BODY_EXAMPLE":
        return new MissingRequestBodyExampleRule();
      case "MISSING_RESPONSE_EXAMPLE":
        return new MissingResponseExampleRule();
      default:
        throw new RuntimeException("Unknown rule");
    }
  }


}
