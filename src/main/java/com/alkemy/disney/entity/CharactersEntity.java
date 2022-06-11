package com.alkemy.disney.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "characters")
@Getter
@Setter
public class CharactersEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long character_id;

    private String image;

    private String name;

    private Integer age;

    private Double weight;

    private String story;

    @ManyToMany(mappedBy = "character")
    private List<MovieSerieEntity> movie_serie = new ArrayList<>();
}
