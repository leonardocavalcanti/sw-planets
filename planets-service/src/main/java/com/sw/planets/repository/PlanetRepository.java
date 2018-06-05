package com.sw.planets.repository;

import com.sw.planets.domain.Planet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends CrudRepository<Planet, Integer> {
    Iterable<Planet> findByName(String name);
}