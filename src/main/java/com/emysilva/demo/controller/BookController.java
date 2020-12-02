package com.emysilva.demo.controller;

import com.emysilva.demo.model.Book;
import com.emysilva.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
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

	@GetMapping("/user/books/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Book> getABook(@PathVariable Long id) {
		Book book = bookService.getABook(id);

		return ResponseEntity.ok(book);
	}

	@GetMapping("/pub/books")
	@PreAuthorize("hasRole('PUBLISHER')")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = bookService.getAllBooks();

		return ResponseEntity.ok(books);
	}

	@PostMapping("/pub/books/status/{id}")
	@PreAuthorize("hasRole('PUBLISHER')")
	public ResponseEntity<Book> updateABookStatus(@PathVariable Long id) {
		Book newBook = bookService.updateABookStatus(id);

		return ResponseEntity.ok(newBook);
	}

	@PostMapping("/pub/books")
	@PreAuthorize("hasRole('PUBLISHER')")
	public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
		Book newBook = bookService.createBook(book);

		return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
	}

	@PutMapping("/admin/books/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Book> updateABook(@PathVariable Long id, @Valid @RequestBody Book book) {
		Book newBook = bookService.updateABook(id, book);

		return ResponseEntity.ok(newBook);
	}

	@DeleteMapping("/admin/books/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteABook(@PathVariable Long id) {
		bookService.deleteABook(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}