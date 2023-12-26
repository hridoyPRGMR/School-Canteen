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

            boolean exit=false;
            StudentInsertion stuIn = new StudentInsertion();
            StudentUpdate stuUp=new StudentUpdate();
            CustomerInsert cuIn=new CustomerInsert();

            System.out.println("User Manual: ");
            System.out.println("Options: 'inSt' -> Student Insertion.");
            System.out.println("Options: 'upSt' -> Student Info Update");
            System.out.println("Options: 'exit' ->Close program.");

            while(!exit){
                System.out.println("Enter Command: ");
                String option = in.readLine();

                switch (option) {
                    case "inSt":
                        stuIn.studentInsert(con, stmt, in);
                        break;
                    case "upSt":
                        stuUp.studentUpdate(con, stmt, in);
                        break;
                    case "inCu":
                        cuIn.customerInsert(con, stmt, in);
                        break;
                    case "exit":
                        exit=true;
                        System.out.println("Exiting program...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;
                }
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


