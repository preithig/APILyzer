package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.api.SubCategoryEnum;
import io.swagger.v3.oas.models.OpenAPI;

import java.util.Arrays;
import java.util.List;

public abstract class MissingDescriptionRule extends AbstractRuleSpecification {

  List<MissingDescriptionRule> list = Arrays.asList(new MissingInfoDescriptionRule(), new MissingServerDescriptionRule());

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


}
