package src.project1;

import java.sql.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StudentCRUD {
    public static void main(String[] args) {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.jdbc.Driver"); 

            // Establish connection 
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost/college", "root", ""
            );

            // Reader for taking input from user
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String choice;

            // Loop until the user chooses to exit
            do {
                // Display menu
                System.out.println("\n---- Student CRUD Menu ----");
                System.out.println("1. Insert");
                System.out.println("2. Update");
                System.out.println("3. Delete");
                System.out.println("4. Display All");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                choice = br.readLine().trim();

                switch (choice) {
                case "1": // Insert
                    String insertMore = null;
                    do {
                        int id;
                        try {
                            System.out.print("Enter id: ");
                            id = Integer.parseInt(br.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid ID. Please enter a number.");
                            continue;
                        }

                        // Check if ID exists
                        PreparedStatement check = con.prepareStatement("SELECT id FROM student WHERE id = ?");
                        check.setInt(1, id);
                        ResultSet existing = check.executeQuery();
                        if (existing.next()) {
                            System.out.println("Student with this ID already exists.");
                            continue;
                        }

                        // Collect details
                        System.out.print("Enter name: ");
                        String name = br.readLine();
                        System.out.print("Enter city: ");
                        String city = br.readLine();
                        System.out.print("Enter state: ");
                        String state = br.readLine();
                        System.out.print("Enter pincode: ");
                        String pin = br.readLine();

                        // Insert into database
                        PreparedStatement ps1 = con.prepareStatement(
                            "INSERT INTO student (id, name, city, state, pincode) VALUES (?, ?, ?, ?, ?)"
                        );
                        ps1.setInt(1, id);
                        ps1.setString(2, name);
                        ps1.setString(3, city);
                        ps1.setString(4, state);
                        ps1.setString(5, pin);
                        int ins = ps1.executeUpdate();
                        System.out.println(ins + " record inserted.");
                        ps1.close();

                        // Ask for another insert
                        System.out.print("Do you want to insert another record? (y/n): ");
                        insertMore = br.readLine().trim().toLowerCase();
                        if (insertMore.equals("n")) {
                            System.out.println("Exiting program..., Goodbye!");
                            br.close();
                            con.close();
                            System.exit(0); // <- Exit program here
                        }
                    } while (insertMore.equals("y"));
                    break;


                    case "2": // Update
                        int idToUpdate;
                        try {
                            System.out.print("Enter ID to update: ");
                            idToUpdate = Integer.parseInt(br.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid ID.");
                            break;
                        }

                        // New values
                        System.out.print("Enter new name: ");
                        String newName = br.readLine();
                        System.out.print("Enter new city: ");
                        String newCity = br.readLine();
                        System.out.print("Enter new state: ");
                        String newState = br.readLine();
                        System.out.print("Enter new pincode: ");
                        String newPin = br.readLine();

                        // Update 
                        PreparedStatement ps2 = con.prepareStatement(
                            "UPDATE student SET name=?, city=?, state=?, pincode=? WHERE id=?"
                        );
                        ps2.setString(1, newName);
                        ps2.setString(2, newCity);
                        ps2.setString(3, newState);
                        ps2.setString(4, newPin);
                        ps2.setInt(5, idToUpdate);
                        int upd = ps2.executeUpdate();
                        System.out.println(upd + " record updated.");
                        ps2.close();
                        break;

                    case "3": // Delete
                        int idToDelete;
                        try {
                            System.out.print("Enter ID to delete: ");
                            idToDelete = Integer.parseInt(br.readLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid ID.");
                            break;
                        }

                        System.out.print("Are you sure you want to delete this record? (yes/no): ");
                        String confirm = br.readLine().trim().toLowerCase();
                        if (!confirm.equals("yes")) {
                            System.out.println("Delete cancelled.");
                            break;
                        }

                        // Perform delete
                        PreparedStatement ps3 = con.prepareStatement("DELETE FROM student WHERE id=?");
                        ps3.setInt(1, idToDelete);
                        int del = ps3.executeUpdate();
                        System.out.println(del + " record deleted.");
                        ps3.close();
                        break;

                    case "4": // Display all students
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM student");

                        System.out.printf("%-5s %-15s %-15s %-15s %-10s\n", "ID", "Name", "City", "State", "Pincode");
                        System.out.println("--------------------------------------------------------------");
                        while (rs.next()) {
                            System.out.printf("%-5d %-15s %-15s %-15s %-10s\n",
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("city"),
                                rs.getString("state"),
                                rs.getString("pincode")
                            );
                        }
                        rs.close();
                        stmt.close();
                        break;

                    case "5": // Exit
                        System.out.println("Exiting program. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a number from 1 to 5.");
                }
            } while (!choice.equals("5"));

            // Cleanup
            br.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
