package nl.thecheerfuldev.multitenancy.config;

import com.zaxxer.hikari.HikariDataSource;
import nl.thecheerfuldev.multitenancy.core.TenantContext;

import java.sql.Connection;
import java.sql.SQLException;

public class TenantAwareHikariDataSource extends HikariDataSource {

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = super.getConnection();
        String tenantName = TenantContext.getTenantName();
        connection.setSchema(tenantName);
        return connection;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        Connection connection = super.getConnection(username, password);
        String tenantName = TenantContext.getTenantName();
        connection.setSchema(tenantName);
        return connection;
    }

}
