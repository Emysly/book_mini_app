package com.emysilva.demo.controller;

import com.emysilva.demo.model.Book;
import com.emysilva.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping("/home")
	public String allAccess() {
		return "Welcome to book mini app.";
	}
	
	@GetMapping("/user/books")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<List<Book>> getAllBooksByStatus() {
		List<Book> books = bookService.getAllBooksByStatus();

		return ResponseEntity.ok(books);
	}

	@GetMapping("/mod/books")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = bookService.getAllBooks();

		return ResponseEntity.ok(books);
	}

	@PostMapping("/mod/books/status/{id}")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<Book> updateABookStatus(@PathVariable Long id) {
		Book newBook = bookService.updateABookStatus(id);

		return ResponseEntity.ok(newBook);
	}

	@PostMapping("/mod/books")
	@PreAuthorize("hasRole('MODERATOR')")
	public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
		Book newBook = bookService.createBook(book);

		return ResponseEntity.ok(newBook);
	}

	@PutMapping("/admin/books/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> updateABook(@PathVariable Long id, @Valid @RequestBody Book book) {
		Book newBook = bookService.getABook(id);
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

		return ResponseEntity.ok(newBook);
	}

	@DeleteMapping("/admin/books/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteABook(@PathVariable Long id) {
		bookService.deleteABook(id);

		return ResponseEntity.ok().build();
	}

}