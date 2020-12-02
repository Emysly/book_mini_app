package com.emysilva.demo.service.impl;

import com.emysilva.demo.model.Book;
import com.emysilva.demo.model.User;
import com.emysilva.demo.repository.BookRepository;
import com.emysilva.demo.repository.UserRepository;
import com.emysilva.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Book> getAllBooksByStatus() {
        return bookRepository.findAllByStatusTrue();
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getABook(Long id) {
        return bookRepository.findById(id).get();
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
        User user = userRepository.findByUsername(username).get();
        book.setUser(user);
        return bookRepository.save(book);
    }

    @Override
    public void deleteABook(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Book updateABookStatus(Long id) {
        Book book = bookRepository.findById(id).get();
        book.setStatus(true);
        return bookRepository.save(book);
    }
}
