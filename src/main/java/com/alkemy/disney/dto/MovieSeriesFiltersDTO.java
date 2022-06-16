package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieSeriesFiltersDTO {
    private String name;
    private List<Long> genre;
    private String order;

    public MovieSeriesFiltersDTO(String name, List<Long> genre, String order) {
        this.name = name;
        this.genre = genre;
        this.order = order;
    }

    public boolean isASC() {
        return this.order.compareToIgnoreCase("ASC") == 0;
    }

    public boolean isDESC() {
        return this.order.compareToIgnoreCase("DESC") == 0;
    }
}

