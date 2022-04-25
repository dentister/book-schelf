--liquibase formatted sql
CREATE SEQUENCE book_schelf_seq
INCREMENT BY 1
START 1000
NO CYCLE;

CREATE TABLE books (
    id bigint NOT NULL DEFAULT nextval('book_schelf_seq'),
    isbn bigint NOT NULL UNIQUE,
    name varchar(400) NOT NULL,
    author varchar(100) NOT NULL,
    annotation varchar(1000) NULL,
    CONSTRAINT books_pk PRIMARY KEY (id)
);