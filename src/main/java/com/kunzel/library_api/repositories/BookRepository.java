package com.kunzel.library_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kunzel.library_api.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
