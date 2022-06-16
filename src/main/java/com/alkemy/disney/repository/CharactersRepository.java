package com.alkemy.disney.repository;

import com.alkemy.disney.entity.CharactersEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharactersRepository extends JpaRepository<CharactersEntity, Long>, JpaSpecificationExecutor<CharactersEntity> {
    List<CharactersEntity> findAll(Specification<CharactersEntity> spec);
}
