package dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Properties;

public class DbConnectionProvider {

    private static final String PROP_FILE = "Data/database_settings.txt";
    private SQLServerDataSource ds;

    public DbConnectionProvider() throws Exception
    {
        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(PROP_FILE));
        ds=new SQLServerDataSource();
        ds.setServerName(databaseProperties.getProperty("Server"));
        ds.setDatabaseName("Database");
        ds.setUser("User");
        ds.setPassword("Password");
    }

    public Connection getConnection() throws Exception {
        return ds.getConnection();
    }
}
