package com.softwareag.apilyzer.api;

import com.softwareag.apilyzer.model.Issue;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.List;

public interface IRuleSpecification {

  String getRuleName();

  CategoryEnum getCategoryName();

  SubCategoryEnum getSubCategoryName();

  SeverityEnum getSeverity();

  String getSummary();

  String getDescription();

  String getRemedy();

  String getErrorInfo();

  void execute(OpenAPI api);

  boolean fix(OpenAPI openAPI, Object data);

  List<Issue> getIssues();

  int getTotalCount();

  int getSuccessCount();


}
