package com.kunzel.library_api.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kunzel.library_api.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIsbn(String isbn);

    @EntityGraph(attributePaths = "author")
    @Query("""
            SELECT b FROM Book b
            WHERE (:genre IS NULL OR b.genre = :genre)
              AND (:authorId IS NULL OR b.author.id = :authorId)
              AND (:available IS NULL OR b.available = :available)
            """)
    Page<Book> search(@Param("genre") String genre,
            @Param("authorId") Long authorId,
            @Param("available") Boolean available,
            Pageable pageable);
}
