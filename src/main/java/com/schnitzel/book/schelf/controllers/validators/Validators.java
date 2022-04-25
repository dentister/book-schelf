package com.schnitzel.book.schelf.controllers.validators;

import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.schnitzel.book.schelf.book.schelf.api.model.BookWithKeyDto;
import com.schnitzel.book.schelf.exceptions.BookSchelfException;

public class Validators {
    public static Consumer<BookWithKeyDto> validateBookWithKeyDto() {
        return bookDto -> {
            if (bookDto.getIsbn() == null
                    || bookDto.getIsbn() < 0
                    || StringUtils.isAnyBlank(bookDto.getName(), bookDto.getAuthor())) {
                throw new BookSchelfException("Invalid input data");
            }
        };
    }
}
