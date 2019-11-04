package org.softwareag.techinterrupt.apilyzer.model;

public class RuleSet {

  private String category;

  private String score;

  private SubCategory[] subCategory;

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public SubCategory[] getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(SubCategory[] subCategory) {
    this.subCategory = subCategory;
  }
}
