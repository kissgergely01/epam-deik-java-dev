package com.epam.training.ticketservice.core.movie.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Movies")
@Data
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String title;
    private String category;
    private Integer length;


    public Movie(String title, String category, Integer length) {
        this.title = title;
        this.category = category;
        this.length = length;
    }

}