package com.kunzel.library_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kunzel.library_api.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

  @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
  List<Author> searchByName(String name);
}
