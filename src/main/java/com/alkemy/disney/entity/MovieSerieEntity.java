package com.alkemy.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie_or_serie")
@Getter
@Setter
public class MovieSerieEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long movie_serie_id;

    private String image;

    private String title;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creation_date;


    private Integer score;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "movie_or_serie_character",
            joinColumns = @JoinColumn(name = "movie_serie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id")
    )
    private List<CharactersEntity> character = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;
}
