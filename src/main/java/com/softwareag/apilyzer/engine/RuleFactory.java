package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.engine.rules.*;
import org.springframework.stereotype.Component;

@Component
public class RuleFactory {

  private SecuritySchemeRule securitySchemeRule = new SecuritySchemeRule();

  private MissingInfoDescriptionRule missingInfoDescriptionRule = new MissingInfoDescriptionRule();

  private MissingServerDescriptionRule missingServerDescriptionRule = new MissingServerDescriptionRule();

  private Missing2xxResponseRule missing2xxResponseRule = new Missing2xxResponseRule();

  private MissingServerInformationRule missingServerInformationRule = new MissingServerInformationRule();

  private MissingRequestBodyExampleRule missingRequestBodyExampleRule = new MissingRequestBodyExampleRule();

  private MissingResponseExampleRule missingResponseExampleRule = new MissingResponseExampleRule();

  public IRuleSpecification resolveRule(String ruleName) {
    switch (ruleName) {
      case "MISSING_INFO_DESC":
        return missingInfoDescriptionRule;
      case "MISSING_SERVER_DESC":
        return missingServerDescriptionRule;
      case "HTTP_SECURITY_SCHEME":
        return securitySchemeRule;
      case "MISSING_SERVER_INFORMATION":
        return missingServerInformationRule;
      case "OPERATION_2XX_RESPONSE":
        return missing2xxResponseRule;
      case "MISSING_REQUESTBODY_EXAMPLE":
        return missingRequestBodyExampleRule;
      case "MISSING_RESPONSE_EXAMPLE":
        return missingResponseExampleRule;
      default:
        throw new RuntimeException("Unknown rule");
    }
  }


}
