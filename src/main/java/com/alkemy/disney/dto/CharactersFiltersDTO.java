package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CharactersFiltersDTO {
    private String name;
    private Integer age;
    private List<Long> moviesSeriesId;

    public CharactersFiltersDTO(String name, Integer age, List<Long> moviesSeriesId) {
        this.name = name;
        this.age = age;
        this.moviesSeriesId = moviesSeriesId;
    }
}
