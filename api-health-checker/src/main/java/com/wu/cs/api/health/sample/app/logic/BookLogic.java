package com.wu.cs.api.health.sample.app.logic;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wu.cs.api.health.sample.app.logic.exception.EntityFoundException;
import com.wu.cs.api.health.sample.app.logic.exception.EntityNotFoundException;
import com.wu.cs.api.health.sample.app.model.Book;
import com.wu.cs.api.health.sample.app.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * Class that implements the logic to retrieve and store the book entity info 
 * @author carlosdlr
 *
 */
@Service
@Slf4j
public class BookLogic {

  /**
   * Data component to interact with the DB independent of the implementation
   */
  @Autowired
  private BookRepository repository;
  /**
   * Constant messages for logic and info validation
   */
  private static final String NULL_MSG = "The book cannot be null";
  private static final String NULL_ID_MSG = "The book ID cannot be null";
  private static final String BOOK_SAVED_MSG = "The book has been saved ";
  private static final String BOOK_ALREADY_SAVED_MSG = "The book is already present in the DB ";
  private static final String BOOK_IS_NOT_SAVED_MSG = "The book is not present in the DB ";

  /**
   * Method that validates if a book instance exist in the DB
   * @param book instance to validate
   * @return boolean value true if is present in the DB or false if not
   */
  private boolean exists (final Optional<Book> book) throws IllegalArgumentException{

    if(!book.isPresent()) {

      log.error(NULL_MSG);
      throw new IllegalArgumentException(NULL_MSG);

    }else if (book.get().getId() == null) {

      log.error(NULL_ID_MSG);
      throw new IllegalArgumentException(NULL_ID_MSG);

    }else {

      return repository.existsById(book.get().getId());

    }

  }

  /**
   * Method that allows save a book entity
   * @param book
   */
  public Book save (final Optional<Book> book) throws EntityFoundException{
    Book savedBook = null;
    try {

      if(!exists(book)) {
        savedBook = book.get();
        savedBook = repository.save(savedBook);
        log.info(BOOK_SAVED_MSG + savedBook.toString()); 
        
        return savedBook;

      }else {

        log.info(BOOK_ALREADY_SAVED_MSG + book.toString());
        throw new EntityFoundException(BOOK_ALREADY_SAVED_MSG + book.toString());

      }
    } catch (IllegalArgumentException e) {
      throw new EntityFoundException(e.getMessage());
    }

  }

  /**
   * Method that allows find a book by ID
   * @param book to find
   * @return Optional<Book> the book instance if is found it
   */
  public Optional<Book> getByID (final Optional<Book> book) throws EntityNotFoundException{
    Optional<Book> bookResutl = null;

    try {

      if(exists(book)) {

        bookResutl = repository.findById(book.get().getId().longValue());

      }else {

        log.info(BOOK_IS_NOT_SAVED_MSG + book.toString());
        throw new EntityNotFoundException(Book.class, "id", book.get().getId().toString());

      }

      return bookResutl;

    } catch (IllegalArgumentException e) {
      throw new EntityNotFoundException(Book.class, "id", book.get().getId().toString());
    }

  }

}
