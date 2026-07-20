package com.kunzel.library_api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kunzel.library_api.exceptions.BookNotAvailableException;
import com.kunzel.library_api.exceptions.DuplicatedIsbnException;
import com.kunzel.library_api.exceptions.NotFoundException;
import com.kunzel.library_api.model.Author;
import com.kunzel.library_api.model.Book;
import com.kunzel.library_api.repositories.AuthorRepository;
import com.kunzel.library_api.repositories.BookRepository;
import com.kunzel.library_api.repositories.LoanRepository;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;
  private final LoanRepository loanRepository;

  public BookService(BookRepository bookRepository, AuthorRepository authorRepository, LoanRepository loanRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
    this.loanRepository = loanRepository;
  }

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public Book getBookById(Long id) {
    return bookRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public Book createBook(String title, String isbn, int publishedYear, String genre, Long authorId) {
    Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));

    if (isIsbnDuplicated(isbn)) {
      throw new DuplicatedIsbnException(isbn);
    }

    Book book = new Book(title, isbn, publishedYear, genre, author);
    return bookRepository.save(book);
  }

  public Book updateBook(Long bookId, String title, String isbn, Integer publishedYear, String genre, Long authorId) {
    Book book = getBookById(bookId);

    if (title != null)
      book.setTitle(title);

    if (isbn != null) {
      if (isIsbnDuplicated(isbn)) {
        throw new DuplicatedIsbnException(isbn);
      }
      book.setIsbn(isbn);
    }

    if (publishedYear != null)
      book.setPublishedYear(publishedYear);

    if (genre != null)
      book.setGenre(genre);

    if (authorId != null) {
      Author author = authorRepository.findById(authorId).orElseThrow(() -> new NotFoundException(authorId));
      book.setAuthor(author);
    }

    return bookRepository.save(book);
  }

  @Transactional
  public void removeBook(Long bookId) {
    Book book = getBookById(bookId);

    if (!book.isAvailable()) {
      throw new BookNotAvailableException(bookId);
    }

    loanRepository.deleteByBookId(bookId);
    bookRepository.delete(book);
  }

  public Boolean isIsbnDuplicated(String isbn) {
    List<Book> books = bookRepository.findAll();

    for (Book book : books) {
      if (isbn.equals(book.getIsbn())) {
        return true;
      }
    }

    return false;
  }
}
