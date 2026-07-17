package com.kunzel.library_api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kunzel.library_api.exceptions.NotFoundException;
import com.kunzel.library_api.model.Author;
import com.kunzel.library_api.model.Book;
import com.kunzel.library_api.repositories.AuthorRepository;
import com.kunzel.library_api.repositories.BookRepository;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;

  public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Book getBookById(Long id) {
    return bookRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public Book createBook(String title, String isbn, int publishedYear, String genre, Long authorId) {
    Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));

    Book book = new Book(title, isbn, publishedYear, genre, author);
    return bookRepository.save(book);
  }

  public Book updateBook(Long bookId, String title, String isbn, Integer publishedYear, String genre, Long authorId) {
    Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));

    Book book = getBookById(bookId);

    if (title != null)
      book.setTitle(title);

    if (isbn != null)
      book.setIsbn(isbn);

    if (publishedYear != null)
      book.setPublishedYear(publishedYear);

    if (genre != null)
      book.setGenre(genre);

    if (authorId != null)
      book.setAuthor(author);

    return bookRepository.save(book);
  }

  public void removeBook(Long bookId) {
    Book book = getBookById(bookId);
    bookRepository.delete(book);
  }
}
