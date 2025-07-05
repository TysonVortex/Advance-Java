import java.sql.*;

public class PreparedStatement_ex {
    public static void main(String[] args) {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver connected");
            
            // Step 2: Create connection object
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/college", "root", "");
            
            // Insert statement
            PreparedStatement ps = con.prepareStatement("insert into student values(?,?,?)");
            ps.setInt(1, 1);
            ps.setString(2, "John");
            ps.setInt(3, 20);
            
            int i = ps.executeUpdate();
            if (i != 0) {
                System.out.println(i + " record inserted");
            } else {
                System.out.println("record not inserted");
            }
            
            // Update statement
            PreparedStatement stmt = con.prepareStatement("update student set name=?, age=? where id=?");
            stmt.setString(1, "Kudeep");
            stmt.setInt(2, 45);
            stmt.setInt(3, 1);
            
            int u = stmt.executeUpdate();
            System.out.println(u + " record updated");
            
            // Select statement
            PreparedStatement selectStmt = con.prepareStatement("select * from student");
            ResultSet rs = selectStmt.executeQuery();
            
            while (rs.next()) {
                System.out.println("id: " + rs.getInt(1) + " " + rs.getString(2) + " " + rs.getInt(3));
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
