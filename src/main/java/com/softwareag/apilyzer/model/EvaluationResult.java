package com.softwareag.apilyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;

@Document(type = "default", indexName = "evaluations")
public class EvaluationResult {

  @Field(type = FieldType.Text)
  private String apiName;

  @Field(type = FieldType.Keyword)
  private String apiId;

  @Id
  private String id;

  @Field(type = FieldType.Integer)
  private int score;

  @Field(type = FieldType.Nested)
  private List<Category> categories = new ArrayList<>();

  public EvaluationResult() {
  }

  public String getApiName() {
    return apiName;
  }

  public void setApiName(String apiName) {
    this.apiName = apiName;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public List<Category> getCategories() {
    return categories;
  }

  public void setCategories(List<Category> categories) {
    this.categories = categories;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }
}
