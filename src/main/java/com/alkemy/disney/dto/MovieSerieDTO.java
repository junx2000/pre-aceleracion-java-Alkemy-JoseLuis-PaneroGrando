package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
public class MovieSerieDTO {
    private long id;
    private String image;
    @NotNull
    private String title;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "The format must be yyyy-MM-dd")
    private String creationDate;
    @Min(value = 1, message = "The score must be between 1 and 5.")
    @Max(value = 5, message = "The score must be between 1 and 5.")
    private Double score;
    @NotNull
    private List<CharactersDTO> characters;
    @NotNull
    private GenreDTO genre;
}