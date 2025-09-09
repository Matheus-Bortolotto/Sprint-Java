package infra.db;

import java.sql.*;
import java.util.Properties;

public class OracleConnectionFactory {

    static {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver Oracle JDBC não encontrado. Adicione ojdbc11.", e);
        }
    }

    private static Connection tryConnect(String url, String user, String pass) throws SQLException {
        Properties p = new Properties();
        p.setProperty("user", user);
        p.setProperty("password", pass);
        // Opcional: timeout
        DriverManager.setLoginTimeout(10);
        return DriverManager.getConnection(url, p);
    }

    public static Connection getConnection() throws SQLException {
        String user = System.getenv().getOrDefault("ORACLE_USER", "RM555189");
        String pass = System.getenv().getOrDefault("ORACLE_PASSWORD", "040506");

        // 1) SERVICE NAME
        String urlSvc = System.getenv().getOrDefault(
                "ORACLE_URL", "jdbc:oracle:thin:@//oracle.fiap.com.br:1521/orcl");

        // 2) SID (fallback)
        String urlSid = "jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl";

        try {
            return tryConnect(urlSvc, user, pass);
        } catch (SQLException e1) {
            // Ex: ORA-12514 (listener não conhece o service name)
            try {
                return tryConnect(urlSid, user, pass);
            } catch (SQLException e2) {
                SQLException root = (SQLException) (e2.getNextException() != null ? e2.getNextException() : e2);
                throw new SQLException(
                        "Falha na conexão Oracle. Tente conferir URL/ServiceName/SID, usuário e senha.\n" +
                                "Tentativas:\n - " + urlSvc + "\n - " + urlSid + "\n" +
                                "Erro: " + root.getMessage(), e2);
            }
        }
    }
}
