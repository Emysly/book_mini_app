package com.emysilva.demo.service;

import com.emysilva.demo.model.Book;

import java.security.Principal;
import java.util.List;

public interface BookService {
    List<Book> getAllBooksByStatus();

    List<Book> getAllBooks();

    Book getABook(Long id);

    Book createBook(Book book);

    void deleteABook(Long id);

    Book updateABookStatus(Long id);
}
