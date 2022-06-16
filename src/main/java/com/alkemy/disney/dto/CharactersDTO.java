package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class CharactersDTO {
    private Long id;
    private String image;
    @NotNull
    private String name;
    private Integer age;
    private Double weight;
    private String story;
    @NotNull
    private List<MovieSerieDTO> moviesSeries;
}