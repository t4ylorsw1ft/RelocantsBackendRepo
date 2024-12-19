package org.example.relocantsbackend.controller;

import org.example.relocantsbackend.entity.City;
import org.example.relocantsbackend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("")
    public List<City> getAllCities() {
        return cityService.getAllCities();
    }
}
