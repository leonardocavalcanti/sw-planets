package com.sw.planets.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document(collection = "planets")
public class Planet {
    @Id
	public Integer id;

	@NotNull
	public String name;

	@NotNull
	public String climate;

	@NotNull
	public String terrain;

	public Integer movieAppearances;

	public String status;
}