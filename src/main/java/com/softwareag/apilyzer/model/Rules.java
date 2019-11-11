package com.softwareag.apilyzer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

@Document(indexName = "rules", type = "default")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rules {

  @Id
  private String id;

  @Field(index = false)
  private List<RulesConfiguration> rules;

  @Field(type = FieldType.Date)
  private Date creationDate;

  public Rules() {
  }

  public List<RulesConfiguration> getRules() {
    return rules;
  }

  public void setRules(List<RulesConfiguration> rules) {
    this.rules = rules;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }
}
