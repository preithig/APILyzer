package org.softwareag.techinterrupt.apilyzer.endpoints;

import org.softwareag.techinterrupt.apilyzer.model.Book;
import org.softwareag.techinterrupt.apilyzer.service.BookCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/books")
public class BookController {


  private BookCrudService bookCrudService;

  @Autowired
  public void setBookCrudService(BookCrudService bookCrudService) {
    this.bookCrudService = bookCrudService;
  }

  @GetMapping
  public List<Book> list() {
    return bookCrudService.list();
  }

  @PostMapping
  public Book create(@RequestBody Book book) {
    return bookCrudService.save(book);
  }

  @GetMapping("/{id}")
  public Book findById(@PathVariable String id) {
    return bookCrudService.get(id);
  }
}
