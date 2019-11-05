package com.softwareag.apilyzer.api;

import io.swagger.v3.oas.models.OpenAPI;

public interface IRuleSpecification {

    String getRuleName();
    CategoryEnum getCategoryName();
    SubCategoryEnum getSubCategoryName();

    boolean execute(OpenAPI api);

    SeverityEnum getSeverity();

    String getSummary();
    String getDescription();
    String getRemedy();

}
