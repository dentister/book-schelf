package com.schnitzel.book.schelf.exceptions;

public class BookSchelfException extends RuntimeException {
    private static final long serialVersionUID = 8126990654646161357L;

    public BookSchelfException() {
    }

    public BookSchelfException(String message) {
        super(message);
    }

    public BookSchelfException(Throwable cause) {
        super(cause);
    }

    public BookSchelfException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookSchelfException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
