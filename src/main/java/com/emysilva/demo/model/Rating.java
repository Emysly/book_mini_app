package com.emysilva.demo.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ratingId;

    private Double score;

    private Double avg;

    @OneToOne
    private Book book;
}
