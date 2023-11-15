import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SeniorG extends JFrame {
    public JPanel panel;
    private JTable table1;

    Connection conn = null;
    Statement statement = null;
    ResultSet rs = null;

    public SeniorG() {
        UpdateJTable();
    }

    private void UpdateJTable() {
        try {
            conn = Main.ConnectDB();
            String sql = "SELECT * FROM [View_id2]";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            this.table1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
