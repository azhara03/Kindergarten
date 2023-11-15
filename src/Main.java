import javax.swing.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    //Connection conn = null;


    public static Connection ConnectDB() {
        Connection conn = null;

        try {
            String dbURL = "jdbc:sqlserver://localhost:1433;databaseName=kindergartenDB;username=sa;password=user103;Encrypt=True;TrustServerCertificate=True";
            String user = "sa";
            String pass = "user103";
            conn = DriverManager.getConnection(dbURL, user, pass);
            return conn;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
            return null;
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnectDB();
        Authorization form1 = new Authorization();
        form1.setVisible(true);
        form1.setContentPane(form1.jPanel);
        form1.setTitle("Авторизация");
        form1.setSize(500, 350);
        form1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
