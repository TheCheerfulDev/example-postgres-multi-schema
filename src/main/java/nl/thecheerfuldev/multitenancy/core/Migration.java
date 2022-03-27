package nl.thecheerfuldev.multitenancy.core;

import org.flywaydb.core.Flyway;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Component
public class Migration {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;

    public Migration(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void migrate() {
        List<Map<String, Object>> maps = jdbcTemplate
                .queryForList("SELECT schema_name FROM information_schema.schemata WHERE schema_name LIKE 'tenant%'");

        List<String> tenants = maps.stream().map(bla -> (String) bla.get("schema_name")).toList();

        for (String tenant : tenants) {
            TenantContext.setTenantName(tenant);
            Flyway flyway = Flyway.configure().dataSource(dataSource).load();
            flyway.migrate();
            TenantContext.removeTenantName();
        }

    }
}
