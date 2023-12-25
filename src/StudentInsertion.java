import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement; // Importing java.sql.Statement instead of com.mysql.cj.xdevapi.Statement

class StudentInsertion {
    public void studentInsert(Connection con, Statement stmt, BufferedReader in) {
        try {
            System.out.println("Enter number of rows: ");
            int n = Integer.parseInt(in.readLine());

            String sql = "INSERT INTO student(name, class) VALUES(?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            System.out.println("Enter name (String) and class (int)");
            while (n-- > 0) {
                String name = in.readLine();
                int cls = Integer.parseInt(in.readLine());

                pstmt.setString(1, name);
                pstmt.setInt(2, cls);
                
                pstmt.addBatch(); // Add the prepared statement to the batch for bulk execution
            }
            
            pstmt.executeBatch(); // Execute all statements in the batch at once
            System.out.println("Data Inserted");

        } catch (SQLException | NumberFormatException | IOException e) {
            e.printStackTrace();
        } 
    }
}
