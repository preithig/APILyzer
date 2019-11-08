package com.softwareag.apilyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(type = "default", indexName = "apis")
public class Api {

  @Id
  String id;

  @Field(type = FieldType.Text, index = false)
  String api;

  @Field(type = FieldType.Keyword)
  String evaluationId;

  @Field(type = FieldType.Text)
  String apiName;

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

  public String getApi() {
    return api;
  }

  public void setApi(String api) {
    this.api = api;
  }

  public String getEvaluationId() {
    return evaluationId;
  }

  public void setEvaluationId(String evaluationId) {
    this.evaluationId = evaluationId;
  }
}
