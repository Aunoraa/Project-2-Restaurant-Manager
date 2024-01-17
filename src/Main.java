import Database.CreateDatabase;
import UserAuth.LoginGUI;
import UserAuth.Register;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
//        CreateDatabase.createDatabase();
//      Register.registerAdmin();
        LoginGUI.loginGUI();
    }
}