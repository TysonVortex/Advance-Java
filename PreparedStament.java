import java.sql.*;

public class preparedStatement_ex{
  public static void main(string [] args){
    
    try{
      class.forName("com.mysql.jdbc.Driver");
      system.out.println("Driver conneted");
      
      //step2 create connetion Object
      Connetion con = DriverManager.getConnetion("jdbc:mysql://localhost/college","roo","");
      
      preparedStatement ps = con.preparedStatement("insert into student value(?,?,?)");
      
      int i = ps.executeupdate();
      if(i! = 0){
        system.out.println(i+"record inserted");
      } else{
        system.out.println("record not inserted");
      }
    }
    
    preparedStatement stmt= con.preparedStatement("update statement set name=?, age=?, where id=?");
    
    stmt.setString(1,"kudeep");
    stmt.setInt(2,45);
    stmt.setInt(3,4)
    int u = stmt.executeupdate();
    system.out.println(u+"record updeted..")
    
    preparedStatement stmt = con.preparedStatement("delete from student");
    
    ResultSet rs=stmt.executeQuery("selet * from student");
    while(rs.next()){
      system.out.println("id:"+rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3));
    }
    cathch(exception e){
      system.out.println(e);
    }
  }
}