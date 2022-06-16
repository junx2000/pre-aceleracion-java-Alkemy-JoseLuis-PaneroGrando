package com.alkemy.disney.service;

import com.alkemy.disney.dto.CharactersBasicDTO;
import com.alkemy.disney.dto.CharactersDTO;

import java.util.List;

public interface CharactersService {

    CharactersDTO save(CharactersDTO dto);

    CharactersDTO getDetailsById(Long id);

    List<CharactersBasicDTO> getDetailsByFilters(String name, Integer age, List<Long> movies);
    CharactersDTO update(Long id, CharactersDTO dto);

    void delete(Long id);
}
