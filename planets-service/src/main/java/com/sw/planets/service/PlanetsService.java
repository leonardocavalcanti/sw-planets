package com.sw.planets.service;

import com.sw.planets.domain.Planet;

public interface PlanetsService {

	/**
	 * Find all planets or planet by name
	 *
	 * @return planets list
	 */
	Iterable<Planet> find(String name);

	/**
	 * Find one planet by id
	 *
	 * @return planet
	 */
	Planet findOne(Integer id);

	/**
	 * Creates new planets with default parameters
	 *
	 * @param create
	 * @return created planets
	 */
	Planet create(Planet create);

	/**
	 * Validates and applies incoming planets updates
	 *
	 * @param update
	 */
	void update(Integer id, Planet update);

	/**
	 * Validates and deletes planets by id
	 *
	 * @param id
	 */
	void delete(Integer id);
}
