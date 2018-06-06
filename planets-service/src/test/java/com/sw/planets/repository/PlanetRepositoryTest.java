package com.sw.planets.repository;

import com.sw.planets.PlanetsApplication;
import com.sw.planets.domain.Planet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PlanetsApplication.class)
@WebAppConfiguration
public class PlanetRepositoryTest {

    @Autowired
    private PlanetRepository repository;

    @Test
    public void shouldSaveAndFindPlanetById() {
        Planet planet = new Planet();

        planet.id = 1;
        planet.name = "name";
        planet.climate = "climate";
        planet.terrain = "terrain";

        repository.save(planet);

        Planet found = repository.findOne(planet.id);

        assertEquals(planet.name, found.name);
    }
}
