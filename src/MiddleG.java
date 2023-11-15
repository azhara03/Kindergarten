import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MiddleG extends javax.swing.JFrame{
    private JPanel panel;
    private JTable table1;

    Connection conn = null;
    CallableStatement stored_pro = null;
    Statement statement = null;
    ResultSet rs = null;

    public MiddleG() {
        panel.addComponentListener(new ComponentAdapter() {

        });
    }

    private void UpdateJTable(){
        try{
            conn = Main.ConnectDB();
            String sql = "SELECT * FROM [View_id1]";
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
