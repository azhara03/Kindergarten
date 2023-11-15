import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.HashMap;

public class AllChildren extends javax.swing.JFrame {

    public JPanel panel;
    public JPanel panelchill;
    private JTable jTable1;
    private JTextField id;
    private JTextField pNumberD;
    private JTextField param;
    private JComboBox nationalityCB;
    private JButton отчетПоIdButton;
    private JButton обновитьButton;
    private JTextField FIO;
    private JTextField date;
    private JComboBox groupCB;
    private JTextField address;
    private JTextField pNumberM;
    private JComboBox kruzhokCB;
    private JButton отчетButton;
    private JButton добавитьButton;
    private JButton диаграммаButton;
    private JButton удалитьButton;

    Connection conn = null;
    CallableStatement stored_pro = null;
    Statement statement = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    public AllChildren() {
        UpdateJTable();
        open_jComboBox2();
        open_jComboBox3();
        open_jComboBox4();
        groupCB.setSelectedItem(null);
        kruzhokCB.setSelectedItem(null);
        nationalityCB.setSelectedItem(null);

        обновитьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn = Main.ConnectDB();
                    stored_pro = conn.prepareCall("{call update_child (?,?,?,?,?,?,?,?,?)}");
                    stored_pro.setString(1, id.getText());
                    stored_pro.setString(2, FIO.getText());
                    stored_pro.setString(3, date.getText());
                    stored_pro.setString(4, address.getText());
                    stored_pro.setString(5, pNumberD.getText());
                    stored_pro.setString(6, pNumberM.getText());
                    stored_pro.setString(7, (String) groupCB.getSelectedItem());
                    stored_pro.setString(8, (String) kruzhokCB.getSelectedItem());
                    stored_pro.setString(9, (String) nationalityCB.getSelectedItem());
                    stored_pro.execute();
                    JOptionPane.showMessageDialog(null, "Обновить");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
                UpdateJTable();
            }
        });

        добавитьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn = Main.ConnectDB();
                    stored_pro = conn.prepareCall("{call insert_child (?,?,?,?,?,?,?,?)}");
                    stored_pro.setString(1, FIO.getText());
                    stored_pro.setString(2, date.getText());
                    stored_pro.setString(3, address.getText());
                    stored_pro.setString(4, pNumberD.getText());
                    stored_pro.setString(5, pNumberM.getText());
                    stored_pro.setString(6, (String) groupCB.getSelectedItem());
                    stored_pro.setString(7, (String) kruzhokCB.getSelectedItem());
                    stored_pro.setString(8, (String) nationalityCB.getSelectedItem());
                    stored_pro.execute();
                    JOptionPane.showMessageDialog(null, "Сохранить");
                    groupCB.setSelectedItem(null);
                    kruzhokCB.setSelectedItem(null);
                    nationalityCB.setSelectedItem(null);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
                UpdateJTable();
            }
        });

        диаграммаButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO add your handling code here:
                DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                dataset.setValue(35, "Marks", "Отсутствует(не посещают)");
                dataset.setValue(20, "Marks", "Chess");
                dataset.setValue(65, "Marks", "Dancing");
                JFreeChart chart = ChartFactory.createBarChart("Количество посещений кружков ", "Наименование кружков", "Количество посещений в %", dataset, PlotOrientation.VERTICAL, false, true, false);
                CategoryPlot p = chart.getCategoryPlot();
                p.setRangeGridlinePaint(Color.black);
                ChartFrame frame = new ChartFrame("График", chart);
                frame.setVisible(true);
                frame.setSize(650, 550);
            }
        });

        удалитьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conn = Main.ConnectDB();
                    stored_pro = conn.prepareCall("{call delete_child (?)}");
                    stored_pro.setString(1, id.getText());
                    stored_pro.execute();
                    JOptionPane.showMessageDialog(null, "Удалить");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
                UpdateJTable();
            }
        });

        отчетПоIdButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showById();
            }
        });

        отчетButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMeAllReport();
            }
        });

        jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                try {
                    conn = Main.ConnectDB();
                    int row = jTable1.getSelectedRow();
                    String Id = (jTable1.getModel().getValueAt(row, 0).toString());
                    String query = "select * from View_children where id='" + Id + "'";
                    PreparedStatement pst = conn.prepareStatement(query);
                    ResultSet rs = pst.executeQuery();
                    while (rs.next()) {
                        id.setText(rs.getString("id"));
                        FIO.setText(rs.getString("FIO"));
                        date.setText(rs.getString("dateBorn"));
                        address.setText(rs.getString("adress"));
                        pNumberD.setText(rs.getString("telDad"));
                        pNumberM.setText(rs.getString("telMom"));
                        param.setText(rs.getString("id"));
                        Object selectedItem = groupCB.getSelectedItem();
                        Object selectedItem1 = kruzhokCB.getSelectedItem();
                        Object selectedItem2 = nationalityCB.getSelectedItem();
                    }
                    pst.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
            }
        });
    }

    private void UpdateJTable() {
        try {
            conn = Main.ConnectDB();
            String sql = "SELECT * FROM [View_children]";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            this.jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void open_jComboBox2() {
        try {
            String sql = "Select *from [group]";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String groups = rs.getString("groups");
                groupCB.addItem(groups);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void open_jComboBox3() {
        try {
            String sql = "Select *from kruzhok";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String kruzhok = rs.getString("kruzhok");
                kruzhokCB.addItem(kruzhok);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void open_jComboBox4() {
        try {
            String sql = "Select *from nationality";
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();

            while (rs.next()) {
                String nationality = rs.getString("nationality");
                nationalityCB.addItem(nationality);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void showMeAllReport() {

        conn = Main.ConnectDB();

        try {
            String reportPath = "C:/Users/VICTUS/IdeaProjects/Kindergarten/src/reports/reportChildren.jasper"; // Путь к файлу .jasper
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private void showById() {
        try {
            conn = Main.ConnectDB();

            String reportPath = "C:/Users/VICTUS/IdeaProjects/Kindergarten/src/reports/reportChildrenById.jasper";
            HashMap hm = new HashMap();
            hm.put("param", Long.parseLong(param.getText().toString()));
            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, conn);
            JasperViewer viewer = new JasperViewer(jasperPrint, false);
            viewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
