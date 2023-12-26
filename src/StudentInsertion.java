import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

class StudentInsertion {
    public void studentInsert(Connection con, Statement stmt, BufferedReader in) {
        try {
            System.out.println("Enter number of rows: ");
            int n = Integer.parseInt(in.readLine());

            String sql = "INSERT INTO student(name, class) VALUES(?, ?)";
            PreparedStatement pstmt = con.prepareStatement(sql);

            while (n > 0) {
                System.out.println("Enter name (String) and class (int)");
                String name = in.readLine();
                String clas = in.readLine();

                int cls;
                try {
                    cls = Integer.parseInt(clas);
                    pstmt.setString(1, name);
                    pstmt.setInt(2, cls);
                    pstmt.addBatch(); // Add the prepared statement to the batch for bulk execution
                    n--;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid class format. Please enter an integer for class.");
                    continue;
                }
            }

            pstmt.executeBatch(); // Execute all statements in the batch at once
            System.out.println("Data Inserted");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
