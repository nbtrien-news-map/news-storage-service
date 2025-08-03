# News Storage Service

A Spring Boot microservice built with Java and Maven, designed as part of a microservices architecture to read news source data from a PostgreSQL database, process it, send requests to other services via Kafka, validate the received data, and store it back into the database.

## Project Overview

The News Storage Service is a backend microservice that operates as a background processing engine in a distributed system. It reads news source data from a PostgreSQL database, processes it, sends structured requests to other microservices via Kafka, validates the responses, and stores the final results back in the database. Built with Spring Boot, it leverages Spring Data JPA for database operations and Spring Kafka for asynchronous, event-driven communication, ensuring scalability and loose coupling.

## Features

- **Data Processing**: Reads and processes news data from PostgreSQL using Spring Data JPA.
- **Asynchronous Communication**: Publishes structured requests to other microservices via Kafka topics and consumes responses.
- **Response Validation**: Validates incoming data from other services before persisting.
- **Data Persistence**: Stores validated results back to the PostgreSQL database.
- **Efficient Mapping**: Uses MapStruct for type-safe DTO-to-entity conversions.
- **Minimal Boilerplate**: Leverages Lombok to reduce boilerplate code.
- **Microservice Design**: Optimized for scalability and integration in a microservices architecture.

## Technologies Used

- **Java 17**: Programming language for the microservice.
- **Spring Boot 3.5.3**: Framework for building the microservice.
- **Spring Data JPA**: For database operations with PostgreSQL.
- **PostgreSQL**: Relational database for persistent storage.
- **Spring Kafka**: For messaging and integration with other microservices.
- **MapStruct 1.6.0**: For mapping between entities and DTOs.
- **Lombok 1.18.30**: For reducing boilerplate code.
- **Jsoup 1.15.3**: For HTML parsing and processing.
- **Jackson**: For JSON serialization and deserialization.
- **Maven**: Build tool for dependency management and compilation.

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java**: Version 17
- **Maven**: Version 3.6 or higher
- **PostgreSQL**: Version 13 or higher
- **Kafka**: For messaging (e.g., Apache Kafka 3.x)
- **Git**: For version control

## Installation

1. **Clone the repository**:

   ```bash
   git clone https://github.com/nbtrien-news-map/news-storage-service
   ```

2. **Install dependencies**:

   - Install Maven dependencies.

3. **Set up PostgreSQL**:

   - Ensure PostgreSQL is running locally or on a remote server.
   - Create a database for the microservice (e.g., `news_storage_db`).

4. **Set up Kafka**:

   - Ensure a Kafka server is running (e.g., locally or via a managed service).
   - Configure the necessary topics for communication with other microservices.

5. **Set up environment variables**:
   - Create an `application.properties` or `application.yml` file in `src/main/resources` based on the example configuration.
   - Configure database, Kafka, and other settings (see [Environment Variables](#environment-variables)).

## Running the Application

1. **Run in development mode**:

   - Start the microservice using Maven's Spring Boot plugin.

2. **Build for production**:

   - Build the project to create a JAR file.

3. **Run the packaged JAR**:
   - Execute the generated JAR file to start the microservice.

## Environment Variables

Create an `application.properties` file in `src/main/resources` with the following example configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/news_storage_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=news-group
server.port=8080
```

Or use `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/news_storage_db
    username: your_username
    password: your_password
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: news-group
server:
  port: 8080
```

- `spring.datasource.*`: PostgreSQL connection details.
- `spring.kafka.*`: Kafka server, consumer, and producer configuration for microservice communication.

## Author

- GitHub: [nbtrien](https://github.com/nbtrien)
- Website: [portfolio.nbtrien.site](https://portfolio.nbtrien.site)
- Email: trienbanguyen@gmail.com
