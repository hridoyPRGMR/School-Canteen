import java.io.BufferedReader;
import java.sql.*;

class StudentUpdate {
    public void studentUpdate(Connection con, Statement stmt, BufferedReader in) {
        try {
            System.out.println("Not: If you are done with update work, enter '0'");

            String sql = "UPDATE student SET name=?, class=? WHERE id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            boolean exit = false;

            while (!exit) {
                System.out.println("Enter Student Id to update: ");
                String idInput = in.readLine();

                if (idInput.equals("0")) {
                    exit = true;
                    continue; // Exit the loop
                }

                int id;
                try {
                    id = Integer.parseInt(idInput);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid ID format. Please enter a valid number.");
                    continue; // Restart the loop
                }

                System.out.println("Enter Name (String) and Class (int): ");
                String name = in.readLine();

                int cls;
                try {
                    cls = Integer.parseInt(in.readLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Class format. Please enter a valid number.");
                    continue; // Restart the loop
                }

                pstmt.setString(1, name);
                pstmt.setInt(2, cls);
                pstmt.setInt(3, id);

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            System.out.println("Updates applied.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
