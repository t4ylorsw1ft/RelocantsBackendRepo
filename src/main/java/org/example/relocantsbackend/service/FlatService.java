package org.example.relocantsbackend.service;

import org.example.relocantsbackend.dto.flats.FlatFilter;
import org.example.relocantsbackend.entity.Flat;
import org.example.relocantsbackend.repository.FlatRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlatService {

    private final FlatRepository flatRepository;

    @Autowired
    public FlatService(FlatRepository flatRepository) {
        this.flatRepository = flatRepository;
    }

    public List<Flat> getFlatsByFilter(FlatFilter filter) {
        String city = filter.getCity() != null ? filter.getCity() : ""; // По умолчанию пустой город
        int minPrice = filter.getMinPrice() != null ? filter.getMinPrice() : Integer.MIN_VALUE; // По умолчанию минимальная цена
        int maxPrice = filter.getMaxPrice() != null ? filter.getMaxPrice() : Integer.MAX_VALUE; // По умолчанию максимальная цена

        if (city.isEmpty()) {
            return flatRepository.findByPriceRange(minPrice, maxPrice); // Поиск по цене
        } else {
            return flatRepository.findByCityAndPriceRange(city, minPrice, maxPrice); // Поиск по городу и цене
        }
    }

    public List<Flat> getFlatsByCity(String city) {
        return flatRepository.findByCity(city); // Поиск по городу
    }

    public List<Flat> getAllFlats() {
        return flatRepository.findAll(); // Все квартиры
    }
}
