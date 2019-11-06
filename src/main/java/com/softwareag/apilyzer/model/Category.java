package com.softwareag.apilyzer.model;

import java.util.ArrayList;
import java.util.List;

public class Category {

  private String name;

  private double score;

  private List<SubCategory> subCategories = new ArrayList<>();

  public Category() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  public List<SubCategory> getSubCategories() {
    return subCategories;
  }

  public void setSubCategories(List<SubCategory> subCategories) {
    this.subCategories = subCategories;
  }
}
