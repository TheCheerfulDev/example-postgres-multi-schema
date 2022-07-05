package nl.thecheerfuldev.multitenancy.examplepostgresmultischema;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import nl.thecheerfuldev.multitenancy.core.TenantContext;
import nl.thecheerfuldev.multitenancy.domain.BoardGame;
import nl.thecheerfuldev.multitenancy.repository.BoardGameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExamplePostgresMultiSchemaApplicationTests {

    @Container
    private static final PostgreSQLContainer<?> DATABASE =
            new PostgreSQLContainer<>("postgres:14.1")
                    .withClasspathResourceMapping("init.sql", "/docker-entrypoint-initdb.d/init.sql", BindMode.READ_ONLY)
                    .withDatabaseName("multi")
                    .withUsername("postgres")
                    .withPassword("Secret11!");

    @LocalServerPort
    private int localServerPort;

    @Autowired
    private BoardGameRepository boardGameRepository;

    @DynamicPropertySource
    private static void init(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", DATABASE::getJdbcUrl);
        registry.add("spring.datasource.username", DATABASE::getUsername);
        registry.add("spring.datasource.password", DATABASE::getPassword);
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = localServerPort;
    }

    @AfterEach
    void tearDown() {
        List<String> tenants = List.of("tenant1", "tenant2");

        for (String tenant : tenants) {
            TenantContext.setTenantName(tenant);
            this.boardGameRepository.deleteAll();
            TenantContext.removeTenantName();
        }
    }

    @Test
    void no_tenant_contamination() {
        TenantContext.setTenantName("tenant1");
        this.boardGameRepository.save(new BoardGame("Xia", 3, 5));
        assertThat(this.boardGameRepository.findAll()).hasSize(1);

        TenantContext.setTenantName("tenant2");
        assertThat(this.boardGameRepository.findAll()).isEmpty();
    }

    @Test
    void no_tenant_contamination_via_api() {
        Header tenant1Header = new Header("X-TenantID", "tenant1");
        given().header(tenant1Header)
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "name": "Xia",
                          "minPlayers": 3,
                          "maxPlayers": 5
                        }""")
                .when()
                .post("/boardgames")
                .then()
                .statusCode(201);

        BoardGame[] boardGamesTenant1 = given().header(tenant1Header)
                .when()
                .get("/boardgames")
                .then()
                .statusCode(200).extract().body().as(BoardGame[].class);

        assertThat(boardGamesTenant1).hasSize(1);

        Header tenant2Header = new Header("X-TenantID", "tenant2");

        BoardGame[] boardGamesTenant2 = given().header(tenant2Header)
                .when()
                .get("/boardgames")
                .then()
                .statusCode(200).extract().body().as(BoardGame[].class);

        assertThat(boardGamesTenant2).isEmpty();
    }
}
