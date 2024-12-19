package org.example.relocantsbackend.controller;

import org.example.relocantsbackend.entity.Flat;
import org.example.relocantsbackend.service.FlatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/flats")
public class FlatController {

    @Autowired
    private FlatService flatService;

    @GetMapping("/rental")
    public List<Flat> getAllFlats() throws IOException {
        return flatService.getFlats();
    }
}

