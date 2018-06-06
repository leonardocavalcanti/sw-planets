package com.sw.planets.controller;

import com.sw.planets.domain.Planet;
import com.sw.planets.service.PlanetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PlanetsController {

	@Autowired
	private PlanetsService planetsService;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public Iterable<Planet> find(@RequestParam(required = false) String name) {
		return planetsService.find(name);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public Planet findOne(@PathVariable Integer id) {
		return planetsService.findOne(id);
	}

	@RequestMapping(path = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@Valid @RequestBody Planet planet) {
		planetsService.create(planet);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void update(@PathVariable Integer id, @Valid @RequestBody Planet planet) {
		planetsService.update(id, planet);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id) {
		planetsService.delete(id);
	}
}
