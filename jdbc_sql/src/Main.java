import java.sql.*;
import java.util.Scanner;

public class Main {

    static final String URL = "jdbc:mysql://localhost:3306/testdb";
    static final String USER = "root";
    static final String PASS = "";

    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(URL, USER, PASS);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Add Student ===");
        System.out.print("Enter Student ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine();
        System.out.print("Enter Contact: ");
        String contact = scanner.nextLine();
        addStudent(conn, id, name, address, contact);

        System.out.println("\n=== Update Student ===");
        System.out.print("Enter new Name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter new Address: ");
        String newAddress = scanner.nextLine();
        System.out.print("Enter new Contact: ");
        String newContact = scanner.nextLine();
        updateStudent(conn, id, newName, newAddress, newContact);

        System.out.println("\n=== Delete Student ===");
        deleteStudent(conn, id);

        System.out.println("\n=== Remaining Students ===");
        showAllStudents(conn);

        conn.close();
        scanner.close();
    }

    static void addStudent(Connection conn, int id, String name, String address, String contact) throws SQLException {
        String sql = "INSERT INTO students (sId, name, address, contact) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setString(2, name);
        stmt.setString(3, address);
        stmt.setString(4, contact);
        stmt.executeUpdate();
        System.out.println("Student added.");
    }

    static void updateStudent(Connection conn, int id, String name, String address, String contact) throws SQLException {
        String sql = "UPDATE students SET name = ?, address = ?, contact = ? WHERE sId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, address);
        stmt.setString(3, contact);
        stmt.setInt(4, id);
        int rows = stmt.executeUpdate();
        if (rows > 0)
            System.out.println("Student updated.");
        else
            System.out.println("Student not found.");
    }

    static void deleteStudent(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM students WHERE sId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();
        if (rows > 0)
            System.out.println("Student deleted.");
        else
            System.out.println("Student not found.");
    }

    static void showAllStudents(Connection conn) throws SQLException {
        String sql = "SELECT * FROM students";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            System.out.printf("ID: %d, Name: %s, Address: %s, Contact: %s\n",
                    rs.getInt("sId"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("contact"));


        }
    }
}
