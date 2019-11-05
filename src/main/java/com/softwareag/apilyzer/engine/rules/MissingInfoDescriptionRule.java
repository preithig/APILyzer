package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.RuleEnum;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.elasticsearch.common.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MissingInfoDescriptionRule extends MissingDescriptionRule {

  public List<Issue> executeRule(OpenAPI api) {

    Info info = api.getInfo();
    if (Strings.isNullOrEmpty(info.getDescription())) {
      createIssue(buildContext(api));
    }
    return null;
  }

  private Map<String, String> buildContext(OpenAPI api) {

    Map<String, String> context = new HashMap<>();

    return null;
  }

  @Override
  public String getRuleName() {
    return RuleEnum.MISSING_INFO_DESC.getRulename();
  }

  @Override
  public java.lang.String getSummary() {
    return RuleEnum.MISSING_INFO_DESC.getSummary();
  }

  @Override
  public String getDescription() {
    return RuleEnum.MISSING_INFO_DESC.getDescription();
  }

  @Override
  public String getRemedy() {
    return RuleEnum.MISSING_INFO_DESC.getRemedy();
  }

  @Override
  public String getErrorInfo() {
    return null;
  }


}
