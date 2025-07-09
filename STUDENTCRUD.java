import java.sql.*;
import java.io.*;

public class StudentCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/school_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    
    private static Connection conn;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
    public static void main(String[] args) {
        try {
            // Connect to database
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to database!");
            
            // Create table
            createTable();
            
            // Main menu
            while (true) {
                System.out.println("\n1. Insert");
                System.out.println("2. Update (ask id for update)");
                System.out.println("3. Delete (ask id for delete)");
                System.out.println("4. Display all records");
                System.out.println("5. Exit");
                System.out.print("Enter your choice (1-5): ");
                
                int choice = Integer.parseInt(br.readLine());
                
                switch (choice) {
                    case 1:
                        insertStudent();
                        break;
                    case 2:
                        updateStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 4:
                        displayAllStudents();
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        conn.close();
                        return;
                    default:
                        System.out.println("Invalid choice!");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void createTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS students (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY," +
                         "name VARCHAR(100)," +
                         "city VARCHAR(50)," +
                         "state VARCHAR(50)," +
                         "pincode VARCHAR(10))";
            
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }
    
    private static void insertStudent() {
        try {
            do {
                System.out.print("Enter name: ");
                String name = br.readLine();
                
                System.out.print("Enter city: ");
                String city = br.readLine();
                
                System.out.print("Enter state: ");
                String state = br.readLine();
                
                System.out.print("Enter pincode: ");
                String pincode = br.readLine();
                
                String sql = "INSERT INTO students (name, city, state, pincode) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, city);
                pstmt.setString(3, state);
                pstmt.setString(4, pincode);
                
                pstmt.executeUpdate();
                System.out.println("Student inserted successfully!");
                pstmt.close();
                
                System.out.print("Do you want to insert another record? (y/n): ");
                String choice = br.readLine();
                
                if (!choice.equalsIgnoreCase("y")) {
                    System.out.println("Exiting program...");
                    conn.close();
                    System.exit(0);
                }
            } while (true);
        } catch (Exception e) {
            System.out.println("Error in insert operation: " + e.getMessage());
        }
    }
    
    private static void updateStudent() {
        try {
            System.out.print("Enter student ID to update: ");
            int id = Integer.parseInt(br.readLine());
            
            System.out.print("Enter new name: ");
            String name = br.readLine();
            
            System.out.print("Enter new city: ");
            String city = br.readLine();
            
            System.out.print("Enter new state: ");
            String state = br.readLine();
            
            System.out.print("Enter new pincode: ");
            String pincode = br.readLine();
            
            String sql = "UPDATE students SET name=?, city=?, state=?, pincode=? WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, city);
            pstmt.setString(3, state);
            pstmt.setString(4, pincode);
            pstmt.setInt(5, id);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student not found!");
            }
            pstmt.close();
        } catch (Exception e) {
            System.out.println("Error in update operation: " + e.getMessage());
        }
    }
    
    private static void deleteStudent() {
        try {
            System.out.print("Enter student ID to delete: ");
            int id = Integer.parseInt(br.readLine());
            
            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student not found!");
            }
            pstmt.close();
        } catch (Exception e) {
            System.out.println("Error in delete operation: " + e.getMessage());
        }
    }
    
    private static void displayAllStudents() {
        try {
            String sql = "SELECT * FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            System.out.println("\nID\tName\t\tCity\t\tState\t\tPincode");
            System.out.println("------------------------------------------------");
            
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + 
                                 rs.getString("name") + "\t\t" + 
                                 rs.getString("city") + "\t\t" + 
                                 rs.getString("state") + "\t\t" + 
                                 rs.getString("pincode"));
            }
            
            rs.close();
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error in display operation: " + e.getMessage());
        }
    }
}
