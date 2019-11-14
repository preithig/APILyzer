package com.softwareag.apilyzer.api;

public enum CategoryEnum {

  API_STANDARDS("API Standards"),
  EASE_OF_USE("Ease of Use"),
  SECURITY_STANDARDS("Security Standards");
  //PERFORMANCE_STANDARDS;

  private String categoryName;


  CategoryEnum(String categoryName) {
    this.categoryName = categoryName;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public static CategoryEnum get(String categoryName) {
    for (CategoryEnum categoryEnum : CategoryEnum.values()) {
      if (categoryEnum.getCategoryName().equals(categoryName)) {
        return categoryEnum;
      }
    }
    return null;
  }

}
