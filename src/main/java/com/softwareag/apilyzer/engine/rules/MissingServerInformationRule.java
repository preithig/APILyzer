package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.api.SubCategoryEnum;
import io.swagger.v3.oas.models.OpenAPI;

public class MissingServerInformationRule extends AbstractRuleSpecification {
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
  public void execute(OpenAPI api) {

  }
}
