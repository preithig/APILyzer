package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.IRuleSpecification;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.api.SubCategoryEnum;
import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class MissingDescriptionRule implements IRuleSpecification {

  List<MissingDescriptionRule> list = Arrays.asList(new MissingInfoDescriptionRule(), new MissingServerDescriptionRule());
  List<Issue> issues = Collections.emptyList();

  @Override
  public String getRuleName() {
    return null;
  }

  @Override
  public CategoryEnum getCategoryName() {
    return CategoryEnum.EASE_OF_USE;
  }

  @Override
  public SubCategoryEnum getSubCategoryName() {
    return SubCategoryEnum.MISSING_FIELDS;
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

  public abstract Issue executeRule(OpenAPI api);

  @Override
  public void execute(OpenAPI openAPI) {

    for (MissingDescriptionRule rule : list) {
      Issue issue = rule.executeRule(openAPI);
      if (issue != null) {
        issues.add(issue);
      }
    }

  }

  @Override
  public List<Issue> getIssues() {
    return issues;
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
