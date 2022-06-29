package com.alkemy.disney.service;

import com.alkemy.disney.Mapper;
import com.alkemy.disney.dto.*;
import com.alkemy.disney.entity.CharactersEntity;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.entity.MovieSerieEntity;
import com.alkemy.disney.exception.ParamNotFound;
import com.alkemy.disney.exception.ValidationException;
import com.alkemy.disney.repository.CharactersRepository;
import com.alkemy.disney.repository.GenreRepository;
import com.alkemy.disney.repository.MovieSerieRepository;
import com.alkemy.disney.repository.specification.MovieSerieSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieSerieServiceImpl implements MovieSerieService{
    @Autowired
    private Mapper mapper;
    @Autowired
    private MovieSerieRepository movieSerieRepository;
    @Autowired
    private CharactersRepository charactersRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private MovieSerieSpecification movieSerieSpecification;

    @Override
    public MovieSerieDTO save(MovieSerieDTO dto) {
        GenreEntity genreEntity = genreRepository.getReferenceById(dto.getGenre().getId());
        MovieSerieEntity entity = mapper.movieSerieDTO2Entity(dto);
        entity.setGenre(genreEntity);
        MovieSerieEntity entitySaved = movieSerieRepository.save(entity);
        return mapper.movieSerieEntity2DTO(entitySaved, true);
    }

    @Override
    public MovieSerieDTO getDetailsById(Long id) {
        Optional<MovieSerieEntity> entity = movieSerieRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFound("movie ID is not valid");
        }
        return mapper.movieSerieEntity2DTO(entity.get(), true);
    }

    @Override
    public List<MovieSerieBasicDTO> getDetailsByFilters(String name, List<Long> genre, String order) {
        List<MovieSerieDTO> dtos;
        List<MovieSerieBasicDTO> result = new ArrayList<>();
        List<MovieSerieEntity> entities;
        MovieSeriesFiltersDTO filtersDTO = new MovieSeriesFiltersDTO(name, genre, order);
        entities = movieSerieRepository.findAll(movieSerieSpecification.getByFilters(filtersDTO));
        dtos = this.mapper.movieSerieEntityList2DTOList(entities, false);
        for (MovieSerieDTO dto : dtos) {
            result.add(mapper.movieSerieDTO2BasicDTO(dto));
        }
        return result;
    }

    @Override
    public MovieSerieDTO update(Long id, MovieSerieDTO dto) {
        Optional<MovieSerieEntity> entityOptional = movieSerieRepository.findById(id);
        if (!entityOptional.isPresent()) {
            throw new ParamNotFound("movie ID is not valid");
        }
        MovieSerieEntity entity = entityOptional.get();
        if (dto.getImage() != null) entity.setImage(dto.getImage());
        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getCreationDate() != null) entity.setCreationDate(mapper.string2LocalDate(dto.getCreationDate()));
        if (dto.getScore() != null) {
            if (1 <= dto.getScore() && dto.getScore() <= 5) {
                entity.setScore(dto.getScore());
            }
            else {
                throw new ValidationException("score must be between 1 and 5.");
            }
        }
        if (dto.getGenre().getId() != null) {
            Long genreId = dto.getGenre().getId();
            Optional<GenreEntity> genreEntityOptional = genreRepository.findById(genreId);
            if (!genreEntityOptional.isPresent()){
                throw new ValidationException("genre ID is not valid.");
            }
            entity.setGenre(genreEntityOptional.get());
        }
        MovieSerieEntity entityUpdated = movieSerieRepository.save(entity);
        return mapper.movieSerieEntity2DTO(entityUpdated, true);
    }

    @Override
    public void delete(Long id) {
        Optional<MovieSerieEntity> entityOptional = movieSerieRepository.findById(id);
        if (!entityOptional.isPresent()) {
            throw new ParamNotFound("movie ID is not valid");
        }
        movieSerieRepository.deleteById(id);
    }

    @Override
    public void addCharacter(Long movieID, Long characterID){
        Optional<MovieSerieEntity> movieSerieEntityOptional = movieSerieRepository.findById(movieID);
        if (!movieSerieEntityOptional.isPresent()) {
            throw new ParamNotFound("movie ID is not valid");
        }
        Optional<CharactersEntity> charactersEntityOptional = charactersRepository.findById(characterID);
        if (!charactersEntityOptional.isPresent()) {
            throw new ParamNotFound("character ID is not valid");
        }
        MovieSerieEntity movieSerieEntity = movieSerieEntityOptional.get();
        movieSerieEntity.addCharacter(charactersEntityOptional.get());
        movieSerieRepository.save(movieSerieEntity);
    }

    @Override
    public void removeCharacter(Long movieID, Long characterID){
        Optional<MovieSerieEntity> movieSerieEntityOptional = movieSerieRepository.findById(movieID);
        if (!movieSerieEntityOptional.isPresent()) {
            throw new ParamNotFound("movie ID is not valid");
        }
        Optional<CharactersEntity> charactersEntityOptional = charactersRepository.findById(characterID);
        if (!charactersEntityOptional.isPresent()) {
            throw new ParamNotFound("character ID is not valid");
        }
        MovieSerieEntity movieSerieEntity = movieSerieEntityOptional.get();
        CharactersEntity charactersEntity = charactersEntityOptional.get();
        movieSerieEntity.removeCharacter(charactersEntity);
        movieSerieRepository.save(movieSerieEntity);
    }
}