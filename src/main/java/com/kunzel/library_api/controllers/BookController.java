package com.kunzel.library_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kunzel.library_api.dtos.book.BookResponse;
import com.kunzel.library_api.dtos.book.CreateBookRequest;
import com.kunzel.library_api.dtos.book.UpdateBookRequest;
import com.kunzel.library_api.model.Book;
import com.kunzel.library_api.services.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
  private final BookService bookService;

  public BookController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  public List<BookResponse> getAllBooks() {
    return bookService.getAllBooks().stream().map(BookResponse::new).toList();
  }

  @GetMapping("/{id}")
  public ResponseEntity<BookResponse> getBookById(@PathVariable("id") Long id) {
    Book book = bookService.getBookById(id);

    return ResponseEntity.ok().body(new BookResponse(book));
  }

  @PostMapping
  public ResponseEntity<BookResponse> createBook(@Valid @RequestBody CreateBookRequest request) {
    Book createdBook = bookService.createBook(
        request.title(),
        request.isbn(),
        request.publishedYear(),
        request.genre(),
        request.authorId());

    return ResponseEntity.status(HttpStatus.CREATED).body(new BookResponse(createdBook));
  }

  @PutMapping("/{id}")
  public ResponseEntity<BookResponse> updateBook(@PathVariable("id") Long id, @RequestBody UpdateBookRequest request) {
    Book updatedBook = bookService.updateBook(id, request.title(),
        request.isbn(),
        request.publishedYear(),
        request.genre(),
        request.authorId());

    return ResponseEntity.ok().body(new BookResponse(updatedBook));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
    bookService.removeBook(id);
    return ResponseEntity.noContent().build();
  }
}
