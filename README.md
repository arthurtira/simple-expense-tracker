## Simple expenses tracker application

This is a simple implementation of an expenses tracking application, to help capture and visualize your expenses. 

The application is made up of the database layer which uses PostgreSQL, Back-end APIs (Spring Boot) and Angular front-end. 

To simplify the deployment we are going to use Docker, particularly docker compose

---

### Prerequisites

In order to run this application you need to have: *Docker* & *Docker Compose* running in your system. 

### How to run it?
1. Clone the repository to yor local machine and in your terminal go to the root directory of the project, where the docker-compose.yml file is located
2. Run the following command: 

$ docker-compose up -d

This command will start the application in the following order:
1. PostgresSQL Database container
2. Backend service container
3. Frontend service container

Depending on your internet speed this can take sowhere vetween 2-5 minutes as the applications will need to download decencies when building the docker images. 

To stop the application use the following command:  


$ docker-compose down


---

#### tracker-postgres (Database)

PostgreSQL database contains only single schema with two tables - scrum
and task table.

Like other parts of application Postgres database is containerized and
the definition of its Docker container can be found in
docker-compose.yml file.

```yml
tracker-postgres:
    image: "postgres:9.6-alpine"
    container_name: tracker-postgres
    volumes:
      - tracker-data:/var/lib/postgresql/data
    ports:
      - 5432:5432
    environment:
      - POSTGRES_DB:tracker
      - POSTGRES_USER:postgres
      - POSTGRES_PASSWORD:password
```


#### tracker-service (REST API)

This is a Spring Boot (Java) based application that connects with a
database that and expose the REST endpoints that can be consumed by
frontend. It supports multiple HTTP REST methods like GET, POST, PUT and
DELETE for the expense domain.

The Swagger documentation for all the available endpoints can be found in Swagger UI, on this link: *http://localhost:8080/api/swagger-ui.html*

The Dockerfile for the service is in the tracker-service root folder.


#### tracker-client (Frontend)

This is an Angular web application which the user can use to manage their expenses. It consumes the REST API endpoints provided by
the tracker-service.

The Dockerfile for this service is in the tracker-client root folder.

It can be accessed using link: *http://localhost:4200/*