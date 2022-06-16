package com.alkemy.disney.repository.specification;

import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.dto.MovieSeriesFiltersDTO;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.entity.MovieSerieEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieSerieSpecification {
    public Specification<MovieSerieEntity> getByFilters(MovieSeriesFiltersDTO filtersDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasLength(filtersDTO.getName())) {
                predicates.add(
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("title")),
                                "%" + filtersDTO.getName().toLowerCase() + "%")
                );
            }
            if (filtersDTO.getGenre() != null) {
                Join<MovieSerieEntity, GenreEntity> join = root.join("genre");
                Expression<String> genreId = join.get("id");
                predicates.add(genreId.in(filtersDTO.getGenre()));
            }
            // Order Resolver
            String orderByField = "title";
            query.orderBy(
                    filtersDTO.isASC() ?
                            criteriaBuilder.asc(root.get(orderByField)) :
                            criteriaBuilder.desc(root.get(orderByField))
            );
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
