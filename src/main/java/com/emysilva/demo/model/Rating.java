package com.emysilva.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Book book;
}
