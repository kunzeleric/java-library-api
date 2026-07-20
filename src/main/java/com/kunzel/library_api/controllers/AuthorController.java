package com.kunzel.library_api.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.kunzel.library_api.dtos.author.AuthorDetailResponse;
import com.kunzel.library_api.dtos.author.AuthorResponse;
import com.kunzel.library_api.dtos.author.CreateAuthorRequest;
import com.kunzel.library_api.dtos.author.UpdateAuthorRequest;
import com.kunzel.library_api.model.Author;
import com.kunzel.library_api.services.AuthorService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/authors")
public class AuthorController {
  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @GetMapping
  public ResponseEntity<Page<AuthorResponse>> fetchAuthors(Pageable pageable) {
    Page<AuthorResponse> authors = authorService.getAllAuthors(pageable).map(AuthorResponse::new);
    return ResponseEntity.ok().body(authors);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AuthorDetailResponse> getAuthorById(@PathVariable("id") long id) {
    Author author = authorService.getAuthorById(id);
    return ResponseEntity.ok().body(new AuthorDetailResponse(author));
  }

  @PostMapping
  public ResponseEntity<AuthorResponse> createAuthor(@Valid @RequestBody CreateAuthorRequest request) {
    Author createdAuthor = authorService.createAuthor(request.name(), request.nationality(), request.birthDate());
    return ResponseEntity.status(HttpStatus.CREATED).body(new AuthorResponse(createdAuthor));
  }

  @PutMapping("/{id}")
  public ResponseEntity<AuthorResponse> updateAuthor(@PathVariable("id") long id,
      @Valid @RequestBody UpdateAuthorRequest request) {
    Author updatedAuthor = authorService.updateAuthor(id, request.name(), request.nationality(), request.birthDate());
    return ResponseEntity.ok().body(new AuthorResponse(updatedAuthor));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAuthor(@PathVariable("id") long id) {
    authorService.deleteAuthor(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<List<AuthorResponse>> getAuthorsByName(@NotBlank @PathVariable("name") String name) {
    List<AuthorResponse> authors = authorService.getAuthorsByName(name).stream().map(AuthorResponse::new).toList();
    return ResponseEntity.ok().body(authors);
  }
}
