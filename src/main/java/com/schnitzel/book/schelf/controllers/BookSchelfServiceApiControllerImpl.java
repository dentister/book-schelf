package com.schnitzel.book.schelf.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.schnitzel.book.schelf.book.schelf.api.delegate.BookSchelfServiceApiDelegate;
import com.schnitzel.book.schelf.book.schelf.api.model.BookDto;
import com.schnitzel.book.schelf.book.schelf.api.model.BookWithKeyDto;
import com.schnitzel.book.schelf.book.schelf.api.model.PageBookWithKeyDto;
import com.schnitzel.book.schelf.book.schelf.api.model.PageRequestDto;
import com.schnitzel.book.schelf.controllers.adapters.MappingFunctions;
import com.schnitzel.book.schelf.db.model.Book;
import com.schnitzel.book.schelf.services.BookSchelfService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookSchelfServiceApiControllerImpl implements BookSchelfServiceApiDelegate {
    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 200;

    private final BookSchelfService bookSchelfService;

    @Override
    public ResponseEntity<Void> create(BookWithKeyDto bookDto) {
        Book book = MappingFunctions.toBookWithKey().apply(bookDto);

        bookSchelfService.create(book);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long isbn) {
        bookSchelfService.delete(isbn);

        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<BookWithKeyDto>> search(BookWithKeyDto bookDto) {
        Book example = MappingFunctions.toBookWithKey().apply(bookDto);

        List<BookWithKeyDto> resultList = bookSchelfService.search(example).stream()
                .map(book -> MappingFunctions.map(BookWithKeyDto.class).apply(book))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultList);
    }
    
    @Override
    public ResponseEntity<PageBookWithKeyDto> extSearch(BookWithKeyDto bookDto, PageRequestDto pageRequestDto) {
        PageRequest pageRequest = PageRequest.of(
                pageRequestDto.getPage() == null ? DEFAULT_PAGE : pageRequestDto.getPage(),
                pageRequestDto.getPageSize() == null ? DEFAULT_PAGE_SIZE : pageRequestDto.getPageSize());

        Book example = MappingFunctions.toBookWithKey().apply(bookDto);

        Page<Book> books = bookSchelfService.search(example, pageRequest);

        return ResponseEntity.ok(MappingFunctions.pageToPageResponse(PageBookWithKeyDto.class).apply(books));
    }

    @Override
    public ResponseEntity<List<BookWithKeyDto>> getAll() {
        System.out.println("ResponseEntity<List<BookWithKeyDto>> getAll() was called.");

        List<BookWithKeyDto> resultList = bookSchelfService.getAll().stream()
                .map(book -> MappingFunctions.map(BookWithKeyDto.class).apply(book))
                .collect(Collectors.toList());

        return ResponseEntity.ok(resultList);
    }

    @Override
    public ResponseEntity<Void> update(Long isbn, BookDto updateBookDto) {
        System.out.printf("BookSchelfServiceApiControllerImpl.update [isbn={}, bookDto={}]\n", isbn, updateBookDto);
        
        Book book = MappingFunctions.toBook().apply(updateBookDto);

        bookSchelfService.update(isbn, book);

        return ResponseEntity.ok().build();
    }
}
