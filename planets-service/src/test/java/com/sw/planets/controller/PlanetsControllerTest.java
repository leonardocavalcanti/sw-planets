package com.sw.planets.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.planets.PlanetsApplication;
import com.sw.planets.domain.Planet;
import com.sw.planets.service.PlanetsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PlanetsApplication.class)
@WebAppConfiguration
public class PlanetsControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    private PlanetsController planetsController;

    @Mock
    private PlanetsService planetsService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(planetsController).build();
    }

    @Test
    public void shouldCreateNewPlanet() throws Exception {
        final Planet planet = new Planet();
        planet.name = "name";
        planet.climate = "climate";
        planet.terrain = "terrain";

        String json = mapper.writeValueAsString(planet);

        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailCreateWhenPlanetIsNotValid() throws Exception {
        Planet planet = new Planet();

        String json = mapper.writeValueAsString(planet);

        mockMvc.perform(post("/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldUpdatePlanet() throws Exception {
        Planet planet = getStubPlanet();
        String json = mapper.writeValueAsString(planet);

        when(planetsService.findOne(planet.id)).thenReturn(planet);

        mockMvc.perform(put("/" + planet.id).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFailUpdateWhenPlanetIsNotValid() throws Exception {
        Planet planet = new Planet();

        String json = mapper.writeValueAsString(planet);

        mockMvc.perform(put("/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldDeletePlanet() throws Exception {
        Planet planet = getStubPlanet();

        when(planetsService.findOne(planet.id)).thenReturn(planet);

        mockMvc.perform(delete("/" + planet.id))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPlanetById() throws Exception {
        Planet planet = getStubPlanet();

        when(planetsService.findOne(planet.id)).thenReturn(planet);

        mockMvc.perform(get("/" + planet.id))
                .andExpect(jsonPath("$.name").value(planet.name))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetPlanetByName() throws Exception {
        Planet planet = getStubPlanet();

        ArrayList<Planet> planets = new ArrayList<>();
        planets.add(planet);

        when(planetsService.find(planet.name)).thenReturn(planets);

        mockMvc.perform(get("/?name=" + planet.name))
                .andExpect(jsonPath("$[0].name").value(planet.name))
                .andExpect(status().isOk());
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
