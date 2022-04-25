package com.schnitzel.book.schelf.controllers.adapters;

import java.util.function.Function;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.schnitzel.book.schelf.book.schelf.api.model.BookDto;
import com.schnitzel.book.schelf.book.schelf.api.model.BookWithKeyDto;
import com.schnitzel.book.schelf.db.model.Book;

public class MappingFunctions {
    private static final ModelMapper mapper = new ModelMapper();

    public static Function<BookWithKeyDto, Book> toBookWithKey() {
        return source -> {
            return Book.builder().isbn(source.getIsbn()).name(source.getName()).author(source.getAuthor())
                    .annotation(source.getAnnotation()).build();
        };
    }

    public static Function<BookDto, Book> toBook() {
        return source -> {
            return Book.builder().name(source.getName()).author(source.getAuthor()).annotation(source.getAnnotation())
                    .build();
        };
    }

    @SuppressWarnings("rawtypes")
    public static <T> Function<Page, T> pageToPageResponse(Class<T> clazz) {
        return source -> mapper.map(source, clazz);
    }
}
