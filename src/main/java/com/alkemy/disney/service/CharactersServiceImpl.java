package com.alkemy.disney.service;

import com.alkemy.disney.Mapper;
import com.alkemy.disney.dto.CharactersBasicDTO;
import com.alkemy.disney.dto.CharactersDTO;
import com.alkemy.disney.entity.CharactersEntity;
import com.alkemy.disney.repository.CharactersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharactersServiceImpl implements CharactersService {
    @Autowired
    private Mapper mapper;
    @Autowired
    private CharactersRepository charactersRepository;

    @Override
    public CharactersDTO save(CharactersDTO dto) {
        CharactersEntity entity = mapper.charactersDTO2Entity(dto);
        CharactersEntity entitySaved = charactersRepository.save(entity);
        return mapper.charactersEntity2DTO(entitySaved, true);
    }

    @Override
    public CharactersDTO getDetailsById(Long id) {
        CharactersEntity entity = charactersRepository.getReferenceById(id);
        return mapper.charactersEntity2DTO(entity, true);
    }

    @Override
    public  List<CharactersBasicDTO> getDetailsByFilters(String name, Integer age, Long movies) {

        //TODO with specifications. return all for now
        List<CharactersDTO> dtos;
        List<CharactersBasicDTO> result = new ArrayList<>();
        List<CharactersEntity> entities = charactersRepository.findAll();
        dtos = mapper.charactersEntityList2DTOList(entities, false);
        for (CharactersDTO dto : dtos) result.add(mapper.characterDTO2BasicDTO(dto));
        return result;
    }

    @Override
    public CharactersDTO update(Long id, CharactersDTO dto) {
        CharactersEntity entity = charactersRepository.getReferenceById(id);
        if (dto.getImage() != null) entity.setImage(dto.getImage());
        if (dto.getName() != null) entity.setName(dto.getName());
        if (dto.getAge() != null) entity.setAge(dto.getAge());
        if (dto.getWeight() != null) entity.setWeight(dto.getWeight());
        if (dto.getStory() != null) entity.setStory(dto.getStory());

        CharactersEntity entityUpdated = charactersRepository.save(entity);
        return mapper.charactersEntity2DTO(entityUpdated, true);
    }

    @Override
    public void delete(Long id) {
        charactersRepository.deleteById(id);
    }
}