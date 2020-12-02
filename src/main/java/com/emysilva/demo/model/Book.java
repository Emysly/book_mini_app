package com.emysilva.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bookId;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private String createdDate;
    private String updatedDate;
    private Integer numberOfPages;
    private Integer inStockNumber;
    private Integer isbn;
    private Double price;
    private boolean status = false;
    private String author;

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Rating rating;


}
