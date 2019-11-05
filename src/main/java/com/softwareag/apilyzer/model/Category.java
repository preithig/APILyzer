package com.softwareag.apilyzer.model;

import java.util.List;

public class Category {

  private String name;

  private int score;

  private List<SubCategory> subCategory;

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

  public List<SubCategory> getSubCategory() {
    return subCategory;
  }

  public void setSubCategory(List<SubCategory> subCategory) {
    this.subCategory = subCategory;
  }
}
