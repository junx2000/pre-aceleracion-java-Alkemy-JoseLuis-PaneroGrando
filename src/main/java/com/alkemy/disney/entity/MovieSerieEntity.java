package com.alkemy.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie_or_serie")
@Getter
@Setter
@SQLDelete(sql = "UPDATE movie_or_serie SET deleted = true WHERE movie_or_serie_id=?")
@Where(clause = "deleted=false")
public class MovieSerieEntity {

    @Id
    @Column(name = "movie_or_serie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String image;
    private String title;
    @Column(name = "creation_date")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate creationDate;
    private Double score;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "movie_or_serie_character",
            joinColumns = @JoinColumn(name = "movie_or_serie_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id")
    )
    private List<CharactersEntity> characters = new ArrayList<>();
    @ManyToOne()
    @JoinColumn(name = "genre_id")
    private GenreEntity genre;
    private Boolean deleted = Boolean.FALSE;

    /** Functions **/
    public void addCharacter(CharactersEntity entity) {
        this.characters.add(entity);
    }

    public void removeCharacter(CharactersEntity entity) {
        this.characters.remove(entity);
    }
}
