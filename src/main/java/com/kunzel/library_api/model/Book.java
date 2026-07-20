package com.kunzel.library_api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.kunzel.library_api.exceptions.BookNotAvailableException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String title;
  @Column(nullable = false, unique = true)
  private String isbn;
  @Column(nullable = false)
  private int publishedYear;
  @Column(nullable = false)
  private String genre;
  @Column(nullable = false)
  private boolean available;

  @ManyToOne
  @JoinColumn(name = "author_id", nullable = false)
  @JsonBackReference
  private Author author;

  protected Book() {
  }

  public Book(String title, String isbn, int publishedYear, String genre, Author author) {
    this.title = title;
    this.isbn = isbn;
    this.publishedYear = publishedYear;
    this.genre = genre;
    this.available = true;
    this.author = author;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public int getPublishedYear() {
    return publishedYear;
  }

  public void setPublishedYear(int publishedYear) {
    this.publishedYear = publishedYear;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public boolean isAvailable() {
    return available;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public void markAsLoaned() {
    if (!this.isAvailable()) {
      throw new BookNotAvailableException(this.id);
    }

    this.available = false;
  }

  public void markAsReturned() {
    this.available = true;
  }
}
