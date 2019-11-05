package com.softwareag.apilyzer.repository;

import com.softwareag.apilyzer.model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String> {

  List<Book> findByAuthor(String author);

}
