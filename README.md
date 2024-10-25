# Spring Reactive with Kotlin, REST and GraphQL

## Summary

A project with main goal to have a hands-on experience with GraphQL and the Spring Reactive Web project for non-blocking code execution and have a CRUD application. I've chosen to use CQS here in combination with Vertical Slices to have a clearer separation of concerns and also because I know the code would be very short so fitting everything related to a specific feature seemed to me like a great choice.
For this project I didn't focus on the domain and event driven architecture.
I also took the chance to practice Kotlin in a backend context instead of Android Development as usual.
Since I'm using Kotlin, I could have skipped using Spring Reactive Web and rely on Kotlin's built-in support for non-blocking, Coroutines, maybe it's a nice idea for another project.
During the project I stumbled upon some challenges/details I didn't foresee when starting with this:

1. Spring Reactive Web is not compatible with JPA and jdbc, you really need to use r2dbc only.
2. Since you can't use JPA, there's not create-drop feature for development, so I opted out to use Flyway with a migration schema to create the table and trash the docker container whenever I wanted to delete data from the db
3. Streaming Flux of responses with GraphQL seems not to be possible, which is an interesting feature of Spring Reactive Web project
4. The really visualize the stream of responses, Swagger is not the best tool for the job, just use Postman or the Browser instead
5. Catching and Handling exceptions doesn't seem very straight forward when using REST. I didn't explore much about this, maybe later on. As for GraphQL, the return is just null, maybe there's a more descriptive way instead of swallowing exceptions and throw in a null for the frontend.

## Dependencies

1. Spring Web
2. Spring Reactive Web
3. PostgreSQL Driver
4. Spring Data R2DBC
5. Spring for GraphQL
6. Swagger (springdoc-openapi-starter-webmvc-ui)
7. Flyway

## To run the project

1. For the moment I've just containerized the database, later on I might also add the application

```bash
docker-compose up -d
```

2. Once the application is started you can navigate to http://localhost:8080/swagger-ui/index.html to use the REST Controllers or navigate to http://localhost:8080/graphiql to use the GraphQL UI and shoot requests to the backend
