package com.softwareag.apilyzer.api;

import io.swagger.v3.oas.models.OpenAPI;

import java.util.Map;

public interface IRuleSpecification {

    String getRuleName();
    CategoryEnum getCategoryName();
    SubCategoryEnum getSubCategoryName();
    SeverityEnum getSeverity();

    String getSummary();
    String getDescription();
    String getRemedy();
    String getErrorInfo();

    // used for locating the resource -> method -> parameter
    Map<String, String> getContext();

    boolean execute(OpenAPI api);
    boolean fix(OpenAPI openAPI, Object data);

}
