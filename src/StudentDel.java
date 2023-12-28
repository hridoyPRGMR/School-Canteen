import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class StudentDel {
    public void studentDel(Connection con, Statement stmt, BufferedReader in) {
        try {
            System.out.println("Note: Type 'exit' in any option to go back");
            boolean exit = false;

            // Prepared statements for SQL queries
            String sqlD = "DELETE FROM student WHERE id=?";
            PreparedStatement pstmtDel = con.prepareStatement(sqlD);

            String sqlSelect = "SELECT id, name FROM student WHERE id=?";
            PreparedStatement pstmtSelect = con.prepareStatement(sqlSelect);

            String sqlBillInfo = "SELECT s.id, s.name, c.bill, c.customerId " +
                                 "FROM student s INNER JOIN canteencustomer c " +
                                 "ON s.id = c.studentId WHERE s.id=?";
            PreparedStatement pstmtShow = con.prepareStatement(sqlBillInfo);

            while (!exit) {
                System.out.println("Enter Student Id to Delete");
                String idInput = in.readLine();

                if (idInput.equals("exit")) {
                    exit = true;
                    break;
                }

                int studentId;
                try {
                    studentId = Integer.parseInt(idInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Data Type. Please enter an Integer type data.");
                    continue;
                }

                try {
                    // Check if student exists
                    pstmtSelect.setInt(1, studentId);
                    ResultSet res = pstmtSelect.executeQuery();

                    if (res.next()) {
                        int deletedId = res.getInt("id");
                        String name = res.getString("name");

                        pstmtDel.setInt(1, studentId);
                        int affected = pstmtDel.executeUpdate();

                        if (affected > 0) {
                            System.out.println("Student Id: " + deletedId + " | Name: " + name + " | - has been deleted");
                        }
                    } else {
                        System.out.println("Student with ID " + studentId + " doesn't exist.");
                    }

                } catch (SQLException e) {
                    System.out.println("Error occurred!! Student has due in Canteen");

                    // Show payment information for the student
                    pstmtShow.setInt(1, studentId);
                    ResultSet res = pstmtShow.executeQuery();

                    if (res.next()) {
                        int id = res.getInt(1);
                        String name = res.getString(2);
                        int bill = res.getInt(3);
                        int cid = res.getInt(4);

                        System.out.println("Student Id: " + id + " Name: " + name + " Bill: " + bill + " Customer Id: " + cid);
                    }
                    continue;
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
