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
import java.util.Map;

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


  public abstract void executeRule(OpenAPI api);

  @Override
  public void execute(OpenAPI openAPI) {
    for (MissingDescriptionRule rule : list) {
      rule.executeRule(openAPI);
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


  protected Issue createIssue(Map<String, String> context) {
    Issue issue = new Issue();
    issue.setDescription(getDescription());
    issue.setErrorInfo(getErrorInfo());
    issue.setName(getRuleName());
    issue.setRemedy(getRemedy());
    issue.setSeverity(getSeverity().name());
    issue.setSummary(getSummary());
    issue.setContext(context);
    return issue;
  }

}
