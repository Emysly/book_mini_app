package com.emysilva.demo.service.impl;

import com.emysilva.demo.exception.BookNotFoundException;
import com.emysilva.demo.exception.IdNotFoundException;
import com.emysilva.demo.exception.UserNotFoundException;
import com.emysilva.demo.model.Book;
import com.emysilva.demo.model.User;
import com.emysilva.demo.repository.BookRepository;
import com.emysilva.demo.repository.UserRepository;
import com.emysilva.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Book> getAllBooksByStatus() {
        List<Book> books = bookRepository.findAllByStatusTrue();
        if (books.isEmpty()) {
            throw new BookNotFoundException("book not found");
        }
        return books;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new BookNotFoundException("book not found");
        }
        return books;
    }

    @Override
    public Book getABook(Long id) {
        if (id < 0) throw new RuntimeException("id must not be less than 0");
        return bookRepository.findById(id).orElseThrow(() -> new IdNotFoundException("book with id " + id + "not found"));
    }

    @Override
    public Book createBook(Book book) {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal. toString();
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("user not found"));
        book.setUser(user);
        return bookRepository.save(book);
    }

    @Override
    public void deleteABook(Long id) {
        if (id < 0) throw new RuntimeException("id must not be less than 0");
        Book book = bookRepository.findById(id).orElseThrow(() -> new IdNotFoundException("book with id " + id + "not found"));
        bookRepository.delete(book);
    }

    @Override
    public Book updateABookStatus(Long id) {
        if (id < 0) throw new RuntimeException("id must not be less than 0");
        Book book = bookRepository.findById(id).orElseThrow(() -> new IdNotFoundException("book with id " + id + "not found"));
        book.setStatus(true);
        return bookRepository.save(book);
    }
}
