package com.schnitzel.book.schelf.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.schnitzel.book.schelf.db.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByIsbn(Long isbn);

    long deleteByIsbn(Long isbn);
}
