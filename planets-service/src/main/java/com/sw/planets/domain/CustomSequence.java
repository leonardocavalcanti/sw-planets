package com.sw.planets.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customSequences")
public class CustomSequence {
    @Id
    public String id;

    public Integer seq;
}