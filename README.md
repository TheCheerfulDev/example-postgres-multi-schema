# Multi tentant based on schema's

This example project is a POC for a multi tenant application based on spring boot and postgres.

Each tenant gets its own schema.

## Setup

* You can use the provided docker-compose.yml file to deploy a postgres database.
* You must make the 'multi' database manually.
* Run scripts/make_schema_and_tables.sql file on the 'multi' database.
* To test your application, i've provided IntelliJ HTTP requests in the 'requests' folder.