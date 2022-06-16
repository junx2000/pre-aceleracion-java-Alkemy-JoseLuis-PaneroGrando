package com.alkemy.disney.service;

import com.alkemy.disney.Mapper;
import com.alkemy.disney.dto.*;
import com.alkemy.disney.entity.CharactersEntity;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.entity.MovieSerieEntity;
import com.alkemy.disney.repository.CharactersRepository;
import com.alkemy.disney.repository.GenreRepository;
import com.alkemy.disney.repository.MovieSerieRepository;
import com.alkemy.disney.repository.specification.MovieSerieSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        MovieSerieEntity entity = movieSerieRepository.getReferenceById(id);
        return mapper.movieSerieEntity2DTO(entity, true);
    }

    @Override
    public List<MovieSerieBasicDTO> getDetailsByFilters(String name, List<Long> genre, String order) {
        List<MovieSerieDTO> dtos;
        List<MovieSerieBasicDTO> result = new ArrayList<>();
        List<MovieSerieEntity> entities;

        if(name != null || genre != null) {
            MovieSeriesFiltersDTO filtersDTO = new MovieSeriesFiltersDTO(name, genre, order);
            entities = movieSerieRepository.findAll(movieSerieSpecification.getByFilters(filtersDTO));
            dtos = this.mapper.movieSerieEntityList2DTOList(entities, false);
            for (MovieSerieDTO dto : dtos) {
                result.add(mapper.movieSerieDTO2BasicDTO(dto));
            }
        }
        else {
            entities = movieSerieRepository.findAll();
            dtos = mapper.movieSerieEntityList2DTOList(entities, false);
            for (MovieSerieDTO dto : dtos) result.add(mapper.movieSerieDTO2BasicDTO(dto));
        }
        return result;
    }

    @Override
    public MovieSerieDTO update(Long id, MovieSerieDTO dto) {
        MovieSerieEntity entity = movieSerieRepository.getReferenceById(id);
        if (dto.getImage() != null) entity.setImage(dto.getImage());
        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getCreationDate() != null) entity.setCreationDate(mapper.string2LocalDate(dto.getCreationDate()));
        if (dto.getScore() != null) entity.setScore(dto.getScore());
        if (dto.getGenre().getId() != null) {
            Long genreId = dto.getGenre().getId();
            GenreEntity genreEntity = genreRepository.getReferenceById(genreId);
            entity.setGenre(genreEntity);
        }
        MovieSerieEntity entityUpdated = movieSerieRepository.save(entity);
        return mapper.movieSerieEntity2DTO(entityUpdated, true);
    }

    @Override
    public void delete(Long id) {
        movieSerieRepository.deleteById(id);
    }

    @Override
    public void addCharacter(Long movieID, Long characterID){
        MovieSerieEntity movieSerieEntity = movieSerieRepository.getReferenceById(movieID);
        CharactersEntity charactersEntity = charactersRepository.getReferenceById(characterID);
        movieSerieEntity.addCharacter(charactersEntity);
        movieSerieRepository.save(movieSerieEntity);
    }

    @Override
    public void removeCharacter(Long movieID, Long characterID){
        MovieSerieEntity movieSerieEntity = movieSerieRepository.getReferenceById(movieID);
        CharactersEntity charactersEntity = charactersRepository.getReferenceById(characterID);
        movieSerieEntity.removeCharacter(charactersEntity);
        movieSerieRepository.save(movieSerieEntity);
    }
}
