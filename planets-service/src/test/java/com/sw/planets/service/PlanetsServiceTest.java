package com.sw.planets.service;

import com.sw.planets.domain.CustomSequence;
import com.sw.planets.domain.Planet;
import com.sw.planets.repository.PlanetRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class PlanetsServiceTest {

    @InjectMocks
    private PlanetsService planetService;

    @Mock
    private PlanetRepository repository;

    @Mock
    private MongoOperations mongo;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void shouldCreatePlanet() {
        Planet planet = new Planet();
        planet.name = "name";
        planet.climate = "climate";
        planet.terrain = "terrain";

        when(repository.findByName(planet.name)).thenReturn(new ArrayList<>());

        when(mongo.findAndModify((Query) notNull(), (Update) notNull(), (FindAndModifyOptions) notNull(), any())).thenReturn(new CustomSequence());

        planetService.create(planet);

        verify(repository, times(1)).save(planet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailWhenPlanetAlreadyExists() {
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(getStubPlanet());

        when(repository.findByName(getStubPlanet().name)).thenReturn(planets);

        planetService.create(getStubPlanet());
    }

    @Test
    public void shouldUpdatePlanet() {
        Planet planet = getStubPlanet();

        when(repository.findOne(planet.id)).thenReturn(planet);

        planetService.update(planet.id, planet);

        verify(repository, times(1)).save(planet);
    }

    @Test
    public void shouldDeletePlanet() {
        Planet planet = getStubPlanet();

        when(repository.findOne(planet.id)).thenReturn(planet);

        planetService.delete(planet.id);

        verify(repository, times(1)).delete(planet);
    }

    @Test
    public void shouldGetPlanets() {
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(getStubPlanet());

        when(repository.findAll()).thenReturn(planets);

        Iterable<Planet> result = planetService.find(null);

        Planet planet = getStubPlanet();

        Assert.isTrue(result.iterator().next().name.equals(planet.name), "Test failed");
    }

    @Test
    public void shouldGetPlanetByName() {
        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(getStubPlanet());

        when(repository.findByName(getStubPlanet().name)).thenReturn(planets);

        Iterable<Planet> result = planetService.find(getStubPlanet().name);

        Planet planet = getStubPlanet();

        Assert.isTrue(result.iterator().next().name.equals(planet.name), "Test failed");
    }

    @Test
    public void shouldGetPlanetById() {
        Planet planet = getStubPlanet();

        when(repository.findOne(planet.id)).thenReturn(planet);

        Assert.isTrue(planetService.findOne(planet.id).name.equals(planet.name), "Test failed");
    }

    @Test
    public void shouldGetMovieAppearances() throws ExecutionException, InterruptedException {
        Planet planet = getStubPlanet();

        Assert.isTrue(planetService.getMovieAppearances(planet).get(), "Test failed");
    }

    private Planet getStubPlanet() {
        Planet planet = new Planet();
        planet.id = 1;
        planet.name = "name";
        planet.climate = "climate";
        planet.terrain = "terrain";

        return planet;
    }
}
