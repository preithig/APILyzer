package org.softwareag.techinterrupt.apilyzer.service;

import org.softwareag.techinterrupt.apilyzer.model.Book;
import org.softwareag.techinterrupt.apilyzer.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class BookCrudService {

  private BookRepository bookRepository;

  @Autowired
  public void setBookRepository(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public List<Book> list() {
    Iterable<Book> bookIterable = bookRepository.findAll();
    return StreamSupport.stream(bookIterable.spliterator(), false).collect(Collectors.toList());
  }

  public Book save(Book book) {
    return bookRepository.save(book);
  }

  public Book get(String id) {
    Optional<Book> optional = bookRepository.findById(id);
    return optional.get();
  }


}
