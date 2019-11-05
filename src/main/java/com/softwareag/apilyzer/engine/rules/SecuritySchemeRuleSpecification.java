package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.api.SubCategoryEnum;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

public class SecuritySchemeRuleSpecification implements IRuleSpecification {
  @Override
  public String getRuleName() {
    return null;
  }

  @Override
  public CategoryEnum getCategoryName() {
    return CategoryEnum.SECURITY_STANDARDS;
  }

  @Override
  public SubCategoryEnum getSubCategoryName() {
    return SubCategoryEnum.SECURITY_SCHEMES;
  }

  @Override
  public SeverityEnum getSeverity() {
    return SeverityEnum.LOW;
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

  @Override
  public List<Issue> getIssues() {
    return null;
  }

  @Override
  public int getTotalCount() {
    return 0;
  }

  @Override
  public int getSuccessCount() {
    return 0;
  }
}
