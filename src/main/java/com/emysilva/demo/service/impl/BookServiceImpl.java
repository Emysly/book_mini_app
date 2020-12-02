package com.emysilva.demo.service.impl;

import com.emysilva.demo.exception.BookNotFoundException;
import com.emysilva.demo.exception.IdNotFoundException;
import com.emysilva.demo.exception.UserNotFoundException;
import com.emysilva.demo.model.Book;
import com.emysilva.demo.model.User;
import com.emysilva.demo.repository.BookRepository;
import com.emysilva.demo.repository.UserRepository;
import com.emysilva.demo.service.BookService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    public BookServiceImpl(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

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

    @Override
    public Book updateABook(Long id, Book book) {
        if (id < 0) throw new RuntimeException("id must not be less than 0");
        Book newBook = bookRepository.findById(id).orElseThrow(() -> new IdNotFoundException("book with id " + id + "not found"));
        newBook.setAuthor(book.getAuthor());
        newBook.setCreatedDate(book.getCreatedDate());
        newBook.setDescription(book.getDescription());
        newBook.setInStockNumber(book.getInStockNumber());
        newBook.setIsbn(book.getIsbn());
        newBook.setNumberOfPages(book.getNumberOfPages());
        newBook.setPrice(book.getPrice());
        newBook.setStatus(book.isStatus());
        newBook.setTitle(book.getTitle());
        newBook.setUpdatedDate(book.getUpdatedDate());
        newBook.setRating(book.getRating());
        return newBook;
    }
}
