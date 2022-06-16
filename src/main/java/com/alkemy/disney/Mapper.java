package com.alkemy.disney;

import com.alkemy.disney.dto.*;
import com.alkemy.disney.entity.CharactersEntity;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.entity.MovieSerieEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {

    /****************/
    /** CHARACTERS **/
    /****************/
    public CharactersEntity charactersDTO2Entity(CharactersDTO characterDTO) {
        CharactersEntity entity = new CharactersEntity();
        entity.setImage(characterDTO.getImage());
        entity.setName(characterDTO.getName());
        entity.setAge(characterDTO.getAge());
        entity.setWeight(characterDTO.getWeight());
        entity.setStory(characterDTO.getStory());
        if (characterDTO.getMoviesSeries() != null) entity.setMoviesSeries(movieSerieDTOList2EntityList(characterDTO.getMoviesSeries()));
        return entity;
    }
    public CharactersDTO charactersEntity2DTO(CharactersEntity entity, boolean loadMoviesSeries) {
        CharactersDTO characterDTO = new CharactersDTO();
        characterDTO.setId(entity.getId());
        characterDTO.setImage(entity.getImage());
        characterDTO.setName(entity.getName());
        characterDTO.setAge(entity.getAge());
        characterDTO.setWeight(entity.getWeight());
        characterDTO.setStory(entity.getStory());

        if (loadMoviesSeries) {
            List<MovieSerieDTO> moviesSeriesDTO = movieSerieEntityList2DTOList(entity.getMoviesSeries(), false);
            characterDTO.setMoviesSeries(moviesSeriesDTO);
        }

        return characterDTO;
    }

    public CharactersBasicDTO charactersDTO2BasicDTO(CharactersDTO dto) {
        CharactersBasicDTO basicDTO = new CharactersBasicDTO();
        basicDTO.setImage(dto.getImage());
        basicDTO.setName(dto.getName());
        return basicDTO;
    }

    public  List<CharactersEntity> charactersDTOList2EntityList(List<CharactersDTO> dtos) {
        List<CharactersEntity> entities = new ArrayList<>();
        for (CharactersDTO dto : dtos) {
            entities.add(charactersDTO2Entity(dto));
        }
        return entities;
    }

    public List<CharactersDTO> charactersEntityList2DTOList(List<CharactersEntity> entities, boolean loadMovieSeries) {
        List<CharactersDTO> charactersDTO = new ArrayList<>();
        for (CharactersEntity entity : entities) {
            charactersDTO.add(charactersEntity2DTO(entity, loadMovieSeries));
        }
        return charactersDTO;
    }

    /****************/
    /** MOVIESERIE **/
    /****************/
    public LocalDate string2LocalDate(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(strDate, formatter);
    }

    public MovieSerieEntity movieSerieDTO2Entity(MovieSerieDTO dto) {
        MovieSerieEntity movieSerieEntity = new MovieSerieEntity();
        movieSerieEntity.setImage(dto.getImage());
        movieSerieEntity.setTitle(dto.getTitle());
        movieSerieEntity.setCreationDate(string2LocalDate(dto.getCreationDate()));
        movieSerieEntity.setScore(dto.getScore());

        GenreEntity genreEntity = genreDTO2Entity(dto.getGenre());
        movieSerieEntity.setGenre(genreEntity);

        if (! dto.getCharacters().isEmpty()) {
            movieSerieEntity.setCharacters(charactersDTOList2EntityList(dto.getCharacters()));
        }
        return movieSerieEntity;
    }

    public MovieSerieDTO movieSerieEntity2DTO(MovieSerieEntity entity, boolean loadCharacters) {
        MovieSerieDTO movieSerieDTO = new MovieSerieDTO();
        movieSerieDTO.setId(entity.getId());
        movieSerieDTO.setImage(entity.getImage());
        movieSerieDTO.setTitle(entity.getTitle());
        movieSerieDTO.setCreationDate(entity.getCreationDate().toString());
        movieSerieDTO.setScore(entity.getScore());

        GenreDTO genreDTO = genreEntity2DTO(entity.getGenre());
        movieSerieDTO.setGenre(genreDTO);

        if (loadCharacters) {
            List<CharactersDTO> charactersDTO = charactersEntityList2DTOList(entity.getCharacters(), false);
            movieSerieDTO.setCharacters(charactersDTO);
        }

        return movieSerieDTO;
    }

    public MovieSerieBasicDTO movieSerieDTO2BasicDTO(MovieSerieDTO dto) {
        MovieSerieBasicDTO basicDTO = new MovieSerieBasicDTO();
        basicDTO.setTitle(dto.getTitle());
        basicDTO.setImage(dto.getImage());
        basicDTO.setCreationDate(dto.getCreationDate());
        return basicDTO;
    }

    public List<MovieSerieEntity> movieSerieDTOList2EntityList(List<MovieSerieDTO> dtos) {
        List<MovieSerieEntity> entities = new ArrayList<>();
        for (MovieSerieDTO dto : dtos) {
            entities.add(movieSerieDTO2Entity(dto));
        }
        return entities;
    }

    public List<MovieSerieDTO> movieSerieEntityList2DTOList(List<MovieSerieEntity> entities, boolean loadCharacters) {
        List<MovieSerieDTO> dtos = new ArrayList<>();
        for (MovieSerieEntity entity : entities) {
            dtos.add(movieSerieEntity2DTO(entity, loadCharacters));
        }
        return dtos;
    }

    /****************/
    /**   GENRE    **/
    /****************/
    public GenreEntity genreDTO2Entity(GenreDTO dto) {
        GenreEntity entity = new GenreEntity();
        entity.setImage(dto.getImage());
        entity.setName(dto.getName());
        return entity;
    }

    public GenreDTO genreEntity2DTO(GenreEntity entity) {
        GenreDTO dto = new GenreDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setImage(entity.getImage());
        return dto;
    }
}
