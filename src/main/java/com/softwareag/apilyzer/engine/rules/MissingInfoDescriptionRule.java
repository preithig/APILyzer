package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.elasticsearch.common.Strings;

public class MissingInfoDescriptionRule extends MissingDescriptionRule {

  public Issue executeRule(OpenAPI api) {

    Info info = api.getInfo();

    if (Strings.isNullOrEmpty(info.getDescription())) {
      //createIssue()
    }
    return null;
  }


}
