package com.emysilva.demo;

import com.emysilva.demo.model.*;
import com.emysilva.demo.repository.BookRepository;
import com.emysilva.demo.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookControllerIntegrationTest {

    @Autowired
    private BookRepository bookService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetAllBooks() {
        List<Book> books = bookService.findAll();
        assertNotNull(books);
    }

    @Test
    public void testGetBookById() {
        Book book = bookService.findById(27L).get();
        System.out.println(book.getAuthor());
        assertNotNull(book);
    }

    @Test
    public void testCreateBook() {
        Book book = new Book();
        book.setBookId(1L);
        book.setAuthor("Chinua Achebe");
        book.setCreatedDate("yesterday");
        book.setDescription("Things fall apart Things fall apart");
        book.setInStockNumber(30);
        book.setIsbn(23434433);
        book.setNumberOfPages(230);
        book.setPrice(30.0);
        book.setStatus(false);
        book.setTitle("Things fall apart");
        book.setUpdatedDate("today");
        Rating rating = new Rating();
        rating.setRatingId(1L);
        rating.setAvg(10.0);
        rating.setScore(4.5);
        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");
        user.setPassword("password");
        user.setRoles(Set.of(new Role(ERole.ROLE_USER)));
        user.setUsername("test");
        book.setRating(rating);
        book.setUser(user);
        Book postResponse = bookService.save(book);
        assertNotNull(postResponse);
    }

    @Test
    public void testUpdateEmployee() {
        Book book = bookService.findById(27L).get();
        book.setPrice(50.0);
        Book updatedBook = bookService.save(book);
        assertNotNull(updatedBook);
    }

    @Test
    public void testDeleteEmployee() {
        List<Book> books = bookService.findAll();
        assertNotNull(books);
        bookService.deleteById(29L);
        assertEquals(1, books.size() - 1);
    }
}

