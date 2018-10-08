package com.wu.cs.api.health.sample.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wu.cs.api.health.sample.app.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
