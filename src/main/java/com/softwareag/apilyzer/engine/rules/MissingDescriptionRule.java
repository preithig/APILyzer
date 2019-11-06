package com.softwareag.apilyzer.engine.rules;

import com.softwareag.apilyzer.api.CategoryEnum;
import com.softwareag.apilyzer.api.SeverityEnum;
import com.softwareag.apilyzer.api.SubCategoryEnum;

public abstract class MissingDescriptionRule extends AbstractRuleSpecification {

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

}
