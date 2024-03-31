# Inventory Service
This project is used with a collection of projects that are used to trial CI/CD tools and DevEx tools.

## Technologies used:
1. Java 21
2. Spring Boot
   - Web
   - JPA
   - HATEOAS
3. H2 Database
4. Lombok

## Installing and running the project
### Prerequisites
1. Java 21
2. Maven 3.9.5

### Installing and running
1. Clone the repository cd into the root directory
2. Run `mvn spring-boot:run`

## Using the service
1. Start the service using `mvn spring-boot:run`
2. In a browser, navigate to `localhost:8080/swagger-ui/index.html`
3. Review the OpenAPI documentation