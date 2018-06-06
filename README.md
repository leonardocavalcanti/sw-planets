# sw-planets

This is a simple REST API that consists of a Spring Boot application, in addition to a MongoDB database. The goal is to store planets from the Star Wars franchise and also inform the number of appearances of this planets in the movies.

There's a final version published at [GoogleCloud (using Docker containers on Kubernetes)](http://sw.leonardocavalcanti.com/planets).

#### Methods

Method	| Path	| Description
------------- | ------------------------- | -------------
GET	| /planets/	| Gets a list of planets
GET	| /planets/{id}	| Gets a specific planet by its ID
GET	| /planets/?name={name}	| Gets a specific planet by its name
POST	| /planets/	| Adds new planet
PUT	| /planets/{id}	| Updates planet's information
DELETE	| /planets/{id}	| Deletes a planet

## Run Local

As said above, the application uses Docker containers for deployment, so is also possible to run the database and API just by executing a 'docker-compose up' on the root folder after cloning the project.

Also, if preferred, is possible to build the Java application by executing 'mvn package' on the 'planets-service' folder, and then execute 'docker-compose -f docker-compose.yml -f docker-compose.dev.yml up', which will use your local files to build the docker images locally and then start the containers.

After the containers start, just hit 'http://localhost/planets/' to start playing with the API.
