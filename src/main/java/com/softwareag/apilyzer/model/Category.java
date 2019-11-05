package com.softwareag.apilyzer.model;

public class Category {

  private String name;

  private int score;

  private SubCategory[] subCategory;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public SubCategory[] getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(SubCategory[] subCategory) {
    this.subCategory = subCategory;
  }
}
