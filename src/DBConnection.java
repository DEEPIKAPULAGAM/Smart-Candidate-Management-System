import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // JDBC URL: connects to the MySQL database running on port 3306
    private static final String URL =
            "jdbc:mysql://localhost:3306/candidatedb";

    private static final String USER =
            System.getenv("DB_USER");

    private static final String PASSWORD =
            System.getenv("DB_PASSWORD");

    // Static method allows other classes to connect without creating an object
    public static Connection getConnection() throws SQLException {

        // Check whether database credentials are configured
        if (USER == null || USER.trim().isEmpty() || PASSWORD == null) {
            throw new SQLException(
                    "Database credentials are not configured. " +
                    "Set DB_USER and DB_PASSWORD."
            );
        }

        // Establish and return the connection using JDBC DriverManager
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}