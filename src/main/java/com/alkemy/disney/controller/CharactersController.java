package com.alkemy.disney.controller;

import com.alkemy.disney.dto.CharactersBasicDTO;
import com.alkemy.disney.dto.CharactersDTO;
import com.alkemy.disney.service.CharactersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("characters")
public class CharactersController {

    @Autowired
    private CharactersService charactersService;

    @PostMapping
    public ResponseEntity<CharactersDTO> save(@RequestBody CharactersDTO dto) {
        CharactersDTO result = charactersService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharactersDTO> getDetailsById(@PathVariable Long id) {
        CharactersDTO result = charactersService.getDetailsById(id);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping
    public ResponseEntity<List<CharactersBasicDTO>> getDetailsByFilters(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer age,
            @RequestParam(required = false) Long movies
    ) {
        List<CharactersBasicDTO> result = charactersService.getDetailsByFilters(name, age, movies);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CharactersDTO> update(@PathVariable Long id, @RequestBody CharactersDTO dto) {
        CharactersDTO result = charactersService.update(id, dto);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        charactersService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
