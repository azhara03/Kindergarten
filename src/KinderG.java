import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class KinderG extends javax.swing.JFrame{
    public JPanel panel;
    private JTable table1;

    Connection conn = null;
    CallableStatement stored_pro = null;
    Statement statement = null;
    ResultSet rs = null;

    public KinderG() {

    }

    private void UpdateJTable(){
        try{
            conn = Main.ConnectDB();
            String sql = "SELECT * FROM [View_id4]";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            this.table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        UpdateJTable();
    }
}
