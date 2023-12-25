import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;

public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/school";
        String username = "root";
        String password = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try (Connection con = DriverManager.getConnection(url, username, password);
             Statement stmt = con.createStatement()) {

            StudentInsertion stuIn = new StudentInsertion();

            System.out.println("User Manual: ");
            System.out.println("Options: 'in' -> Student Insertion");

            String option = in.readLine();

            switch (option) {
                case "in":
                    stuIn.studentInsert(con, stmt, in);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


