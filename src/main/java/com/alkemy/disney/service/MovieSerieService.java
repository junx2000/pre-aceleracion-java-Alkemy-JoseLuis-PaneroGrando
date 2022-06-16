package com.alkemy.disney.service;

import com.alkemy.disney.dto.CharactersDTO;
import com.alkemy.disney.dto.MovieSerieBasicDTO;
import com.alkemy.disney.dto.MovieSerieDTO;
import com.alkemy.disney.entity.MovieSerieEntity;

import java.util.List;

public interface MovieSerieService {

    MovieSerieDTO save(MovieSerieDTO dto);

    MovieSerieDTO getDetailsById(Long id);

    List<MovieSerieBasicDTO> getDetailsByFilters(String name, Long genre, String order);

    MovieSerieDTO update(Long id, MovieSerieDTO dto);

    void delete(Long id);

    void addCharacter(Long movieID, Long characterID);

    void removeCharacter(Long movieID, Long characterID);
}
