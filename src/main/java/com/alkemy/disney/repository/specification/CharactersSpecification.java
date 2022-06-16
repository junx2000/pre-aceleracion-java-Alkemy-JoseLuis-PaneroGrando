package com.alkemy.disney.repository.specification;

import com.alkemy.disney.dto.CharactersFiltersDTO;
import com.alkemy.disney.entity.CharactersEntity;
import com.alkemy.disney.entity.MovieSerieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CharactersSpecification {
    public Specification<CharactersEntity> getByFilters(CharactersFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasLength(filtersDTO.getName())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + filtersDTO.getName().toLowerCase() + "%")
                );
            }
            if (filtersDTO.getAge() != null) {
                predicates.add(
                        criteriaBuilder.equal(root.get("age"), filtersDTO.getAge())
                );
            }
            if (!CollectionUtils.isEmpty(filtersDTO.getMoviesSeriesId())) {
                Join<CharactersEntity, MovieSerieEntity> join = root.join("moviesSeries", JoinType.INNER);
                Expression<String> movieId = join.get("id");
                predicates.add(movieId.in(filtersDTO.getMoviesSeriesId()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}