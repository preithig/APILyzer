package com.softwareag.apilyzer.api;

public enum SubCategoryEnum {

  MISSING_FIELDS("Missing Fields"),
  SECURITY_SCHEMES("Security Schemes"),
  RESPONSE_DETAILS("Response Details");

  private String subCategoryEnum;

  SubCategoryEnum(String subCategoryEnum) {
    this.subCategoryEnum = subCategoryEnum;
  }

  public String getSubCategoryEnum() {
    return subCategoryEnum;
  }

  public static SubCategoryEnum get(String subCategoryEnum) {
    for (SubCategoryEnum categoryEnum : SubCategoryEnum.values()) {
      if (categoryEnum.getSubCategoryEnum().equals(subCategoryEnum)) {
        return categoryEnum;
      }
    }
    return null;
  }


}
