# Multi tentant based on schema's

This example project is a POC for a multi tenant application based on spring boot and postgres.

Each tenant gets its own schema.

## Migrations with Flyway

Flyway is being used for the database migrations. You can find the custom logic for Flyway with a multi tenant setup in
`nl.thecheerfuldev.multitenancy.core.Migration`

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

## Testing via API

### IntelliJ HTTP Client

To test this application, I've provided IntelliJ HTTP requests in the `requests` folder.


### Curl

You can test this application with the following curl commands.

POST to tenant1:

```shell
curl -X POST http://localhost:8080/boardgames -H "Content-Type: application/json" -H "X-TenantID: tenant1" -d '{"name": "Xia: Legacy of a drift system", "minPlayers": 3, "maxPlayers": 5}'
curl -X POST http://localhost:8080/boardgames -H "Content-Type: application/json" -H "X-TenantID: tenant1" -d '{"name": "Deep Space D-6", "minPlayers": 1, "maxPlayers": 1}'
```

GET for tenant1:

```shell
curl http://localhost:8080/boardgames -H "X-TenantID: tenant1"
```

POST to tenant2:

```shell
curl -X POST http://localhost:8080/boardgames -H "Content-Type: application/json" -H "X-TenantID: tenant2" -d '{"name": "Catan", "minPlayers": 2, "maxPlayers": 6}'
```

GET for tenant2:

```shell
curl http://localhost:8080/boardgames -H "X-TenantID: tenant2"
```

POST to tenant3:

```shell
curl -X POST http://localhost:8080/boardgames -H "Content-Type: application/json" -H "X-TenantID: tenant3" -d '{"name": "Tiny Epic Dinos", "minPlayers": 2, "maxPlayers": 4}'
```

GET for tenant3:

```shell
curl http://localhost:8080/boardgames -H "X-TenantID: tenant3"
```