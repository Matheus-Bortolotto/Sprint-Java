package infra.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConnectionFactory {

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver Oracle JDBC não encontrado no classpath. Adicione ojdbc11.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        // TENTE PRIMEIRO O SERVICE NAME:
        String url  = System.getenv().getOrDefault("ORACLE_URL", "jdbc:oracle:thin:@//oracle.fiap.com.br:1521/orcl");
        String user = System.getenv().getOrDefault("ORACLE_USER", "RM555189");
        String pass = System.getenv().getOrDefault("ORACLE_PASSWORD", "040506");

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", pass);

        return DriverManager.getConnection(url, props);
    }
}
