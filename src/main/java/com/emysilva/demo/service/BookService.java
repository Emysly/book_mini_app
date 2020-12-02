package com.emysilva.demo.service;

import com.emysilva.demo.exception.BookNotFoundException;
import com.emysilva.demo.exception.IdNotFoundException;
import com.emysilva.demo.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooksByStatus() throws BookNotFoundException;

    List<Book> getAllBooks() throws BookNotFoundException;

    Book getABook(Long id) throws IdNotFoundException;

    Book createBook(Book book);

    void deleteABook(Long id) throws IdNotFoundException;

    Book updateABookStatus(Long id) throws IdNotFoundException;
}
