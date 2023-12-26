import java.io.BufferedReader;
import java.sql.*;

class CustomerInsert {
    public void customerInsert(Connection con, Statement stmt, BufferedReader in) {
        try {
            System.out.println("Note: Type 'exit' in any option to go back");
            boolean exit = false;
            
            String sqlSelect = "SELECT bill FROM canteencustomer WHERE studentId=?";
            PreparedStatement pstmtSelect = con.prepareStatement(sqlSelect);

            String sqlUpdate = "UPDATE canteencustomer SET bill=? WHERE studentId=?";
            PreparedStatement pstmtUpdate = con.prepareStatement(sqlUpdate);

            String sqlInsert = "INSERT INTO canteencustomer(bill,studentId) VALUES(?,?)";
            PreparedStatement pstmtInsert = con.prepareStatement(sqlInsert);

            while (!exit) {
                System.out.println("Enter Bill : Student-Id");
                String billInput = in.readLine();
                if (billInput.equals("exit")) {
                    exit = true;
                    break;
                }
                String studentIdInput = in.readLine();
                if (studentIdInput.equals("exit")) {
                    exit = true;
                    break;
                } 

                int studentId;
                try {
                    studentId = Integer.parseInt(studentIdInput);
                    pstmtSelect.setInt(1, studentId);
                    ResultSet rs = pstmtSelect.executeQuery();

                    if (rs.next()) {
                        int currentBill = rs.getInt("bill");
                        int newBill = Integer.parseInt(billInput);
                        int updatedBill = currentBill + newBill;

                        pstmtUpdate.setInt(1, updatedBill);
                        pstmtUpdate.setInt(2, studentId);
                        pstmtUpdate.executeUpdate();
                        System.out.println("Data Updated: Current Bill :" + updatedBill);
                    } else {
                        // Check if the student ID exists in the 'student' table here
                        String sqlCheck = "SELECT * FROM student WHERE id=?";
                        PreparedStatement pstmtCheck = con.prepareStatement(sqlCheck);
                        pstmtCheck.setInt(1, studentId);
                        ResultSet rsCheck = pstmtCheck.executeQuery();

                        if (rsCheck.next()) {
                            int bill = Integer.parseInt(billInput);
                            pstmtInsert.setInt(1, bill);
                            pstmtInsert.setInt(2, studentId);
                            pstmtInsert.executeUpdate();
                        } else {
                            System.out.println("Student ID doesn't exist");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid data types. Please enter Integer type data");
                    continue;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
