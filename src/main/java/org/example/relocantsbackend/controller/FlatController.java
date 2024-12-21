package org.example.relocantsbackend.controller;

import org.example.relocantsbackend.dto.flats.FlatFilter;
import org.example.relocantsbackend.entity.Flat;
import org.example.relocantsbackend.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flats")
public class FlatController {

    private final FlatService flatService;

    @Autowired
    public FlatController(FlatService flatService) {
        this.flatService = flatService;
    }

    @PostMapping("/getAll")
    public ResponseEntity<List<Flat>> getFlatsByFilter(@RequestBody FlatFilter filter) {
        // Если фильтры не переданы, можно использовать значения по умолчанию в сервисе
        List<Flat> flats = flatService.getFlatsByFilter(filter);
        return ResponseEntity.ok(flats);
    }
}
