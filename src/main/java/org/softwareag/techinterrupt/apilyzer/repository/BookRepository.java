package org.softwareag.techinterrupt.apilyzer.repository;

import org.softwareag.techinterrupt.apilyzer.model.Book;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends ElasticsearchRepository<Book, String> {

  List<Book> findByAuthor(String author);

}
