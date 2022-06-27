package com.alkemy.disney.controller;

import com.alkemy.disney.dto.MovieSerieBasicDTO;
import com.alkemy.disney.dto.MovieSerieDTO;
import com.alkemy.disney.service.MovieSerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieSerieController {

    @Autowired
    private MovieSerieService movieSerieService;

    @PostMapping
    public ResponseEntity<MovieSerieDTO> save(@Valid @RequestBody MovieSerieDTO dto) {
        MovieSerieDTO result = movieSerieService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieSerieDTO> getDetailsById(@PathVariable Long id) {
        MovieSerieDTO result = movieSerieService.getDetailsById(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<List<MovieSerieBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<Long> genre,
            @RequestParam(required = false, defaultValue = "ASC") String order
    ) {
        List<MovieSerieBasicDTO> result = movieSerieService.getDetailsByFilters(name, genre, order);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieSerieDTO> update(@PathVariable Long id, @RequestBody MovieSerieDTO dto) {
        MovieSerieDTO result = movieSerieService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        movieSerieService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{movieId}/characters/{characterId}")
    public ResponseEntity<Void> addCharacter(@PathVariable Long movieId, @PathVariable Long characterId) {
        movieSerieService.addCharacter(movieId, characterId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{movieId}/characters/{characterId}")
    public ResponseEntity<Void> removeCharacter(@PathVariable Long movieId, @PathVariable Long characterId) {
        movieSerieService.removeCharacter(movieId, characterId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
