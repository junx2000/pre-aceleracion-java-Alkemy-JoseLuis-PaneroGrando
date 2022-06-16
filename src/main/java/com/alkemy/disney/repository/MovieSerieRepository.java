package com.alkemy.disney.repository;

import com.alkemy.disney.entity.MovieSerieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieSerieRepository extends JpaRepository<MovieSerieEntity, Long>, JpaSpecificationExecutor<MovieSerieEntity> {
    List<MovieSerieEntity> findAll(Specification<MovieSerieEntity> spec);
}
