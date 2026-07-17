package com.kunzel.library_api.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kunzel.library_api.exceptions.NotFoundException;
import com.kunzel.library_api.model.Author;
import com.kunzel.library_api.repositories.AuthorRepository;

@Service
public class AuthorService {

  private final AuthorRepository authorRepository;

  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }

  public Author getAuthorById(Long id) {
    return authorRepository.findByIdWithBooks(id).orElseThrow(() -> new NotFoundException(id));
  }

  public List<Author> getAuthorsByName(String name) {
    // When getting a list from the database, stream method allows processing the
    // list (as Stream interface - map processes it, then we convert to a List
    // again)
    return authorRepository.searchByName(name);
  }

  public Author createAuthor(String name, String nationality, LocalDate birthDate) {
    Author author = new Author(name, nationality, birthDate);
    authorRepository.save(author);
    return author;
  }

  public Author updateAuthor(Long id, String name, String nationality, LocalDate birthDate) {
    Author author = getAuthorById(id);

    if (name != null)
      author.setName(name);
    if (nationality != null)
      author.setNationality(nationality);
    if (birthDate != null)
      author.setBirthDate(birthDate);

    return authorRepository.save(author);
  }

  public void deleteAuthor(Long id) {
    Author author = getAuthorById(id);

    authorRepository.delete(author);
  }
}
