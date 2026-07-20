package com.kunzel.library_api.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.kunzel.library_api.exceptions.AuthorWithBooksException;
import com.kunzel.library_api.exceptions.DuplicatedAuthorException;
import com.kunzel.library_api.exceptions.NotFoundException;
import com.kunzel.library_api.model.Author;
import com.kunzel.library_api.repositories.AuthorRepository;

import jakarta.transaction.Transactional;

@Service
public class AuthorService {

  private final AuthorRepository authorRepository;

  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public Page<Author> getAllAuthors(Pageable pageable) {
    return authorRepository.findAll(pageable);
  }

  public Author getAuthorById(Long id) {
    return authorRepository.findByIdWithBooks(id).orElseThrow(() -> new NotFoundException(id, "autor"));
  }

  public List<Author> getAuthorsByName(String name) {
    return authorRepository.searchByName(name);
  }

  @Transactional
  public Author createAuthor(String name, String nationality, LocalDate birthDate) {
    ensureNameIsAvailable(name, null);
    Author author = new Author(name, nationality, birthDate);
    authorRepository.save(author);
    return author;
  }

  @Transactional
  public Author updateAuthor(Long id, String name, String nationality, LocalDate birthDate) {
    Author author = getAuthorById(id);

    if (name != null) {
      ensureNameIsAvailable(name, id);
      author.setName(name);
    }

    if (nationality != null)
      author.setNationality(nationality);

    if (birthDate != null)
      author.setBirthDate(birthDate);

    return authorRepository.save(author);
  }

  public void deleteAuthor(Long id) {
    Author author = getAuthorById(id);

    if (author.getBooks().size() > 0) {
      throw new AuthorWithBooksException(id);
    }

    authorRepository.delete(author);
  }

  private void ensureNameIsAvailable(String authorName, Long currentAuthorId) {
    authorRepository.searchAuthorByName(authorName).filter(existing -> !existing.getId().equals(currentAuthorId))
        .ifPresent(existing -> {
          throw new DuplicatedAuthorException(existing.getId(), authorName);
        });
  }
}
