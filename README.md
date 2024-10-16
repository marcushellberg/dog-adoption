### Spring Boot and Vaadin Flow Dog Adoption demo app

This is a simple demo app that demonstrates how to build a web application using Spring Boot and Vaadin Flow. 
The app allows users to browse a list of dogs available for adoption and view details about each dog.
The app uses Spring Modulith and Spring AI. 


## Pre-requisites
- Java 21 or later (GraalVM if you want to run the app as a native image)
- Docker
- OpenAI API key in an environment variable named `OPENAI_API_KEY` (or update the `application.properties` file with your key)

## Running the app

Start the app by running `AdoptionApplication` in your IDE or by running the following command in the project root directory:

```
./mvnw spring-boot:run
```

Launch the app by navigating to [http://localhost:8080](http://localhost:8080) in your web browser.