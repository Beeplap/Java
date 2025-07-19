import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection con = null;

        try {
            // Load Oracle JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish connection
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe",
                    "system",
                    "hi1@2#3$"
            );

            if (con != null)
                System.out.println("✅ Connection successful!");
            else

                System.out.println("❌ Connection failed.");

        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                    System.out.println("🔌 Connection closed.");
                }
            } catch (SQLException e) {
                System.out.println("Error while closing connection.");
                e.printStackTrace();
            }
        }
    }
}
