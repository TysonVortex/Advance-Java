import java.sql.*;

public class SimpleDBExample {
    public static void main(String[] args) {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver"); // Fixed casing
            con = DriverManager.getConnection("jdbc:mysql://localhost/college", "root", "");

            Statement stmt = con.createStatement(); // Fixed casing
            stmt.execute("INSERT INTO student VALUES (1, 'john', 23)");

            System.out.println("Record inserted");

            ResultSet rs = stmt.executeQuery("SELECT * FROM student"); // Fixed type
            while (rs.next()) {
                System.out.println("ID ==> " + rs.getInt(1));
                System.out.println("Name ==> " + rs.getString(2));
                System.out.println("Age ==> " + rs.getInt(3)); // Fixed typo
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if (con != null) con.close(); // Close connection in finally
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }
}




/*
import java.sql.*;

public class StudentDatabase {
    public static void main(String[] args) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish connection
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/college", "root", "");
            
            // Create statement
            stmt = con.createStatement();
            
            // Insert record
            stmt.execute("INSERT INTO student VALUES(1, 'john', 23)");
            System.out.println("Record inserted successfully");
            
            // Query and display records
            rs = stmt.executeQuery("SELECT * FROM student");
            System.out.println("\nStudent Records:");
            System.out.println("----------------");
            
            while(rs.next()) {
                System.out.println("ID: " + rs.getInt(1));
                System.out.println("Name: " + rs.getString(2));
                System.out.println("Age: " + rs.getInt(3));
                System.out.println("----------------");
            }
            
        } catch(ClassNotFoundException e) {
            System.out.println("MySQL Driver not found: " + e.getMessage());
        } catch(SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Close resources in reverse order
            try {
                if(rs != null) rs.close();
                if(stmt != null) stmt.close();
                if(con != null) con.close();
            } catch(SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
}




*/