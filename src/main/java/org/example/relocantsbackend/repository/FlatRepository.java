package org.example.relocantsbackend.repository;

import org.example.relocantsbackend.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlatRepository extends JpaRepository<Flat, Long> {

    @Query("SELECT f FROM Flat f WHERE f.city = :city AND f.price BETWEEN :minPrice AND :maxPrice")
    List<Flat> findByCityAndPriceRange(@Param("city") String city,
                                       @Param("minPrice") int minPrice,
                                       @Param("maxPrice") int maxPrice);

    @Query("SELECT f FROM Flat f WHERE f.price BETWEEN :minPrice AND :maxPrice")
    List<Flat> findByPriceRange(@Param("minPrice") int minPrice,
                                @Param("maxPrice") int maxPrice);

    @Query("SELECT f FROM Flat f WHERE f.city = :city")
    List<Flat> findByCity(@Param("city") String city);

    List<Flat> findAll();
}
