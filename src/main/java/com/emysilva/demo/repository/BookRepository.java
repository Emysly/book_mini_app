package com.emysilva.demo.repository;

import com.emysilva.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByStatusTrue();

    boolean existsByIsbn(Integer isbn);
}
