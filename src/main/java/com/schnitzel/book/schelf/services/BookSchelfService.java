package com.schnitzel.book.schelf.services;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.schnitzel.book.schelf.db.model.Book;
import com.schnitzel.book.schelf.db.repository.BookRepository;
import com.schnitzel.book.schelf.exceptions.BookSchelfException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookSchelfService {
    private final BookRepository bookRepository;

    public void create(Book book) {
        try {
            bookRepository.save(book);
        } catch (DataIntegrityViolationException e) {
            throw new BookSchelfException("ISBN should be unique", e);
        }
    }

    @Transactional
    public void delete(Long isbn) {
        if (bookRepository.deleteByIsbn(isbn) == 0) {
            log.warn("No books with isbn={}", isbn);

            throw new EntityNotFoundException("Book not found");
        }
    }

    public void update(Long isbn, Book delta) {
        Book book = getByIsbn(isbn);

        if (delta.getName() != null) {
            book.setName(delta.getName());
        }

        if (delta.getAnnotation() != null) {
            book.setAnnotation(delta.getAnnotation());
        }

        if (delta.getAuthor() != null) {
            book.setAuthor(delta.getAuthor());
        }

        bookRepository.save(book);
    }

    public Book getByIsbn(Long isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new EntityNotFoundException());
    }
    
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
    
    public List<Book> search(Book probe) {
        return bookRepository.findAll(Example.of(probe));
    }

    public Page<Book> search(Book probe, PageRequest pageRequest) {
        return bookRepository.findAll(Example.of(probe), pageRequest);
    }
    


}
