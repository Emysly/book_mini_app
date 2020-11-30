package com.emysilva.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private Date publicationDate;
    private Integer numberOfPages;
    private Integer inStockNumber;
    private Integer isbn;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_author",  joinColumns= {@JoinColumn(name = "book_id")},
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authorSet;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_publisher",  joinColumns= {@JoinColumn(name = "book_id")},
            inverseJoinColumns = @JoinColumn(name = "publisher_id"))
    private Set<Publisher> publisherSet;

}
