import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashMap;

import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Employees extends JFrame {
    public JTable table1;
    public JTextField id;
    public JTextField address;
    public JTextField FIO;
    public JTextField pNumber;
    public JTextField date;
    public JComboBox kruzhok;
    public JComboBox position;
    public JComboBox group;
    public JButton обновитьButton;
    public JButton добавитьButton;
    public JButton удалитьButton;
    public JButton отчетПоНомеруButton;
    public JButton просмотретьОтчетButton;
    public JPanel jpanel;
    private JTextField param;

    Connection conn = null;
    CallableStatement stored_pro = null;
    Statement statement = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public Employees() {
        UpdateJTable2();
        open_position();
        open_kruzhok();
        open_group();
        position.setSelectedItem(null);
        kruzhok.setSelectedItem(null);
        group.setSelectedItem(null);
        обновитьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn = Main.ConnectDB();
                    stored_pro = conn.prepareCall("{call update_employee (?,?,?,?,?,?,?,?)}");
                    stored_pro.setString(1, id.getText());
                    stored_pro.setString(2, FIO.getText());
                    stored_pro.setString(3, date.getText());
                    stored_pro.setString(4, (String) position.getSelectedItem());
                    stored_pro.setString(5, address.getText());
                    stored_pro.setString(6, pNumber.getText());
                    stored_pro.setString(7, (String) kruzhok.getSelectedItem());
                    stored_pro.setString(8, (String) group.getSelectedItem());
                    stored_pro.execute();
                    JOptionPane.showMessageDialog(null, "Updated");
                    id.setText("");
                    FIO.setText("");
                    date.setText("");
                    position.setSelectedItem(null);
                    address.setText("");
                    pNumber.setText("");
                    kruzhok.setSelectedItem(null);
                    group.setSelectedItem(null);

                    //UpdateJTable2();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
                UpdateJTable2();
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    //TableModel model=jTable2.getModel();
                    conn = Main.ConnectDB();
                    int row = table1.getSelectedRow();
                    String Id = (table1.getModel().getValueAt(row, 0).toString());
                    String query = "select * from View_employee where id='" + Id + "'";
                    PreparedStatement pst = conn.prepareStatement(query);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        id.setText(rs.getString("id"));
                        FIO.setText(rs.getString("FIO"));
                        date.setText(rs.getString("dateBorn"));

                        Object selectedItem = position.getSelectedItem();
                        //position.setText(model.getValueAt(row, 3).toString());
                        address.setText(rs.getString("adress"));
                        pNumber.setText(rs.getString("phoneNumber"));
                        Object selectedItem1 = kruzhok.getSelectedItem();
                        Object selectedItem2 = group.getSelectedItem();

                    }
                    pst.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });

        удалитьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn = Main.ConnectDB();
                    stored_pro = conn.prepareCall("{call delete_employee (?)}");
                    stored_pro.setString(1, id.getText());
                    stored_pro.execute();
                    JOptionPane.showMessageDialog(null, "Deleted");
                    id.setText("");
                    FIO.setText("");
                    date.setText("");
                    position.setSelectedItem(null);
                    address.setText("");
                    pNumber.setText("");
                    kruzhok.setSelectedItem(null);
                    group.setSelectedItem(null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
                UpdateJTable2();
            }
        });
        добавитьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn = Main.ConnectDB();
                    stored_pro = conn.prepareCall("{call insert_employee (?,?,?,?,?,?,?)}");
                    stored_pro.setString(1, FIO.getText());
                    stored_pro.setString(2, date.getText());
                    stored_pro.setString(3, (String) position.getSelectedItem());
                    stored_pro.setString(4, address.getText());
                    stored_pro.setString(5, pNumber.getText());
                    stored_pro.setString(6, (String) kruzhok.getSelectedItem());
                    stored_pro.setString(7, (String) group.getSelectedItem());
                    stored_pro.execute();
                    JOptionPane.showMessageDialog(null, "Saved");
                    FIO.setText("");
                    date.setText("");
                    position.setSelectedItem(null);
                    address.setText("");
                    pNumber.setText("");
                    kruzhok.setSelectedItem(null);
                    group.setSelectedItem(null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
                UpdateJTable2();
            }
        });
        отчетПоНомеруButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showById();
            }
        });
        просмотретьОтчетButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMeAllReport();
            }
        });
    }

    private void UpdateJTable2() {
        try {
            conn = Main.ConnectDB();
            String sql = "SELECT [id], [FIO], [dateBorn], [position], [adress], [phoneNumber],[kruzhok],[groups] FROM [View_employee]";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            this.table1.setModel(DbUtils.resultSetToTableModel(rs));
            //setResizable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void open_position() {
        try {

            String sql = "Select * from position";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String Position = rs.getString("position");
                position.addItem(Position);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void open_kruzhok() {
        try {
            String sql = "Select *from kruzhok";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String Kruzhok = rs.getString("kruzhok");
                kruzhok.addItem(Kruzhok);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void open_group() {

        try {
            String sql = "Select *from [group]";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String groups = rs.getString("groups");
                group.addItem(groups);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void showMeAllReport() {

        try {
            conn = Main.ConnectDB();
            JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\VICTUS\\Documents\\NetBeansProjects\\kindergarten\\src\\kindergarten\\report1.jrxml");
            String sql = "SELECT View_employee.\"id\" AS View_employee_id, View_employee.\"FIO\" AS View_employee_FIO, "
                    + "View_employee.\"dateborn\" AS View_employee_dateborn, View_employee.\"position\" AS View_employee_position,"
                    + " View_employee.\"adress\" AS View_employee_adress, View_employee.\"phoneNumber\" AS View_employee_phoneNumber, "
                    + "View_employee.\"kruzhok\" AS View_employee_kruzhok, View_employee.\"groups\" AS View_employee_groups FROM \"dbo\".\"View_employee\" View_employee";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showById() {
        try {
            conn = Main.ConnectDB();
            JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\VICTUS\\Documents\\NetBeansProjects\\kindergarten\\src\\kindergarten\\newReport1.jrxml");
            HashMap hm = new HashMap();
            hm.put("param_id2", Integer.parseInt(param.getText().toString()));
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, conn);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}