# Multi tentant based on schema's

This example project is a POC for a multi tenant application based on spring boot and postgres.

Each tenant gets its own schema.

## Setup

* You can use the provided docker-compose.yml file to deploy a postgres database.

```shell
docker compose up -d
```

* To remove the deployed database run the following command

```shell
docker compose down
```

## Running the application

* Start the application via your IDE of choice, OR run the following command

```shell
mvn spring-boot:run
```

* To test your application, i've provided IntelliJ HTTP requests in the `requests` folder.