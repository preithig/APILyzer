package com.softwareag.apilyzer.engine;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.api.SubCategoryEnum;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.Map;

public class MissingDescriptionRuleSpecification implements IRuleSpecification {
  @Override
  public String getRuleName() {
    return null;
  }

  @Override
  public CategoryEnum getCategoryName() {
    return null;
  }

  @Override
  public SubCategoryEnum getSubCategoryName() {
    return null;
  }

  @Override
  public SeverityEnum getSeverity() {
    return null;
  }

  @Override
  public String getSummary() {
    return null;
  }

  @Override
  public String getDescription() {
    return null;
  }

  @Override
  public String getRemedy() {
    return null;
  }

  @Override
  public String getErrorInfo() {
    return null;
  }

  @Override
  public Map<String, String> getContext() {
    return null;
  }

  @Override
  public boolean execute(OpenAPI api) {
    return false;
  }

  @Override
  public boolean fix(OpenAPI openAPI, Object data) {
    return false;
  }
}
