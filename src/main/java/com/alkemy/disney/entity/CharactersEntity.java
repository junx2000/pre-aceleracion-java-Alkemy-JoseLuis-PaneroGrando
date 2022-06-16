package com.alkemy.disney.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "characters")
@Getter
@Setter
@SQLDelete(sql = "UPDATE characters SET deleted = true WHERE character_id=?")
@Where(clause = "deleted=false")
public class CharactersEntity {
    @Id
    @Column(name = "character_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String image;
    private String name;
    private Integer age;
    private Double weight;
    private String story;
    @ManyToMany(mappedBy = "characters", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<MovieSerieEntity> moviesSeries = new ArrayList<>();
    private Boolean deleted = Boolean.FALSE;
}
