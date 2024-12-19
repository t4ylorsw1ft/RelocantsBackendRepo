package org.example.relocantsbackend.repository;

import org.example.relocantsbackend.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
