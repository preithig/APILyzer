package com.softwareag.apilyzer.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "books", type = "default")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Book implements Serializable {

  @Id
  private String id;
  @Field(type = FieldType.Text)
  private String title;
  @Field(type = FieldType.Text)
  private String author;
  @Field(type = FieldType.Text)
  private String releaseDate;

  public Book() {
  }

  public Book(String id, String title, String author, String releaseDate) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.releaseDate = releaseDate;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  @Override
  public String toString() {
    return "Book{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", author='" + author + '\'' +
        ", releaseDate='" + releaseDate + '\'' +
        '}';
  }
}
