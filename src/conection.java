import java.sql.*;

public class conection {
    public static void main(String[] args) {
       try{  
Class.forName("oracle.jdbc.OracleDriver");
//step2 create  the connection object  
Connection con=DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/XEPDB1","SYSTEM","1124");  
  
//step3 create the statement object  
Statement stmt=con.createStatement();  
  
//step4 execute query  
ResultSet rs=stmt.executeQuery("select * from CUSTOMER");  
while(rs.next())  
System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3)+"  "+rs.getString(4));  
  
//step5 close the connection object  
con.close();  
  
}catch(Exception e){ System.out.println(e);}  
  
}  
        }

