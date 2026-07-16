import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/candidatedb";
    private static final String USER = "root";
    private static final String PASSWORD = "Deepika@2020";

    public static Connection getConnection() {

        try {
            System.out.println(com.mysql.cj.jdbc.Driver.class.getName());
            
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected Successfully!");
            return con;
        }
        catch(SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return null;
        }

    }
}