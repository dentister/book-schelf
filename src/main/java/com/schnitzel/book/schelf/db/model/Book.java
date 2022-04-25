package com.schnitzel.book.schelf.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name= "book_schelf_seq", sequenceName = "book_schelf_seq", initialValue=1, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.AUTO, generator="book_schelf_seq")
    private Long id;

    @Column(name = "isbn")
    private Long isbn;
    
    @Column(name = "name")
    private String name;

    @Column(name = "author")
    private String author;
    
    @Column(name = "annotation")
    private String annotation;
}
