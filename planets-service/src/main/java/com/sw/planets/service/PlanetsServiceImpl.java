package com.sw.planets.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sw.planets.domain.CustomSequence;
import com.sw.planets.domain.Planet;
import com.sw.planets.repository.PlanetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class PlanetsServiceImpl implements PlanetsService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PlanetRepository repository;

    @Autowired
    private MongoOperations mongo;

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<Planet> find(String name) {
        if (name != null) {
            return repository.findByName(name);
        }

        return repository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Planet findOne(Integer id) {
        return repository.findOne(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Planet create(Planet create) {
        Assert.isTrue(!repository.findByName(create.name).iterator().hasNext(), "Planet already exists");

        // Getting next entry id
        CustomSequence counter = mongo.findAndModify(
                query(where("_id").is("customSequences")),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                CustomSequence.class);

        create.id = counter.seq;

        create.status = "Obtaining movie appearances for planet";

        repository.save(create);

        log.info("New planet has been created: " + create.name);

        // New thread to find movie appearances for planet, without blocking the insert return
        new Thread(() -> {
            try {
                RestTemplate restTemplate = new RestTemplate();

                HttpHeaders headers = new HttpHeaders();
                headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
                headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

                HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

                ResponseEntity<String> response = restTemplate.exchange("https://swapi.co/api/planets/?search=" + create.name, HttpMethod.GET, entity, String.class);

                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(response.getBody());

                    if (root.path("count").asInt() > 1) {
                        log.info("Found too many results while trying to get movie appearances for planet: " + create.name);
                    } else if (root.path("count").asInt() == 0) {
                        log.info("No results for planet " + create.name + " on SWAPI");

                        create.movieAppearances = 0;
                    } else {
                        create.movieAppearances = root.findPath("films").size();
                    }

                    create.status = "Final";

                    repository.save(create);

                    return;
                }
            } catch (Exception e) {
                log.info("Exception while trying to get movie appearances for planet " + create.name + ": " + e.getMessage());

                e.printStackTrace();
            }

            create.status = "Unable to obtain movie appearances for planet";

            repository.save(create);
        }).start();

        return create;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Integer id, Planet update) {
        Planet planet = repository.findOne(id);

        Assert.notNull(planet, "Planet not found");

        planet.name = update.name;
        planet.climate = update.climate;
        planet.terrain = update.terrain;

        repository.save(planet);

        log.debug("Planet {} changes has been saved", update.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Integer id) {
        Planet planet = repository.findOne(id);

        Assert.notNull(planet, "Planet not found");

        repository.delete(planet);

        log.debug("Planet {} changes has been deleted", planet.name);
    }
}