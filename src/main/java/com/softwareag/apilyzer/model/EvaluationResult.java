package com.softwareag.apilyzer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.softwareag.apilyzer.api.CategoryEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(type = "default", indexName = "evaluations")
@JsonInclude(JsonInclude.Include.NON_NULL)

public class EvaluationResult {

  @Field(type = FieldType.Text)
  private String apiName;

  @Field(type = FieldType.Keyword)
  private String apiId;

  @Id
  private String id;

  @Field(type = FieldType.Integer)
  private double score;

  @Field(type = FieldType.Nested)
  private List<Category> categories = new ArrayList<>();

  @Field(type = FieldType.Date)
  private Date evaluationDate;

  public String getApiId() {
    return apiId;
  }

  public void setApiId(String apiId) {
    this.apiId = apiId;
  }

  public Date getEvaluationDate() {
    return evaluationDate;
  }

  public void setEvaluationDate(Date evaluationDate) {
    this.evaluationDate = evaluationDate;
  }

  public EvaluationResult() {
    getCategories().add(new Category(CategoryEnum.API_STANDARDS.name(), 100));
    getCategories().add(new Category(CategoryEnum.EASE_OF_USE.name(), 100));
    getCategories().add(new Category(CategoryEnum.SECURITY_STANDARDS.name(), 100));
    //getCategories().add(new Category(CategoryEnum.PERFORMANCE_STANDARDS.name(), 100));
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

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }
}
