package com.alkemy.disney.service;

import com.alkemy.disney.Mapper;
import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private Mapper mapper;

    @Override
    public GenreDTO save(GenreDTO dto) {
        GenreEntity entity = mapper.genreDTO2Entity(dto);
        GenreEntity entitySaved = genreRepository.save(entity);
        return mapper.genreEntity2DTO(entitySaved);
    }
}
