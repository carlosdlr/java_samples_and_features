package com.wu.cs.api.health.sample.app.api.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wu.cs.api.health.sample.app.logic.BookLogic;
import com.wu.cs.api.health.sample.app.logic.exception.EntityFoundException;
import com.wu.cs.api.health.sample.app.logic.exception.EntityNotFoundException;
import com.wu.cs.api.health.sample.app.model.Book;

@Controller
public class BookController {

  @Autowired
  private BookLogic bookService;

  @GetMapping("/book-info/{bookId}")
  @ResponseBody
  public Book getBookInfo(@PathVariable(name="bookId", required=true) Long bookId) throws EntityNotFoundException{
    Book book = new Book();
    book.setId(bookId);
    book = this.bookService.getByID(Optional.of(book)).get();
    return book;
  }

  @PostMapping("/book-info")
  @ResponseBody
  public Book createStudent(@RequestBody Book book) throws EntityFoundException{
    return bookService.save(Optional.of(book));
  }

}
