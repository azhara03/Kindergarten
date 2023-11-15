import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
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
import java.sql.*;
import java.util.HashMap;


public class AllChildren extends javax.swing.JFrame {

    public JPanel panel;
    private JTable jTable1;
    private JTextField id;
    private JTextField pNumberD;
    private JTextField textField4;
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

    Connection conn =null;
    CallableStatement stored_pro = null;
    Statement statement = null;
    ResultSet rs = null;
    PreparedStatement pst=null;

    public AllChildren() {
       UpdateJTable();
//        open_position();
//        open_kruzhok();
//        open_group();
//        position.(null);
//        kruzhok.setSelectedItem(null);
//        group.(null);
        обновитьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    conn = Main.ConnectDB();
                    stored_pro = conn.prepareCall("{call update_child (?,?,?,?,?,?,?,?,?)}");
                    stored_pro.setString(1, id.getText());
                    stored_pro.setString(2, FIO.getText());
                    stored_pro.setString(3, date.getText());
                    stored_pro.setString(4, address.getText());
                    stored_pro.setString(5, pNumberD.getText());
                    stored_pro.setString(6, pNumberM.getText());
                    stored_pro.setString(7, (String)groupCB.getSelectedItem());
                    stored_pro.setString(8, (String)kruzhokCB.getSelectedItem());
                    stored_pro.setString(9, (String)nationalityCB.getSelectedItem());
                    stored_pro.execute();
                    JOptionPane.showMessageDialog(null, "Обновить");
                }catch(Exception ex){
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, ex);
                }
                UpdateJTable();
            }
        });
        добавитьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    conn = Main.ConnectDB();
                    stored_pro = conn.prepareCall("{call insert_child (?,?,?,?,?,?,?,?)}");
                    stored_pro.setString(1, FIO.getText());
                    stored_pro.setString(2, date.getText());
                    stored_pro.setString(3, address.getText());
                    stored_pro.setString(4, pNumberD.getText());
                    stored_pro.setString(5, pNumberM.getText());
                    stored_pro.setString(6, (String)groupCB.getSelectedItem());
                    stored_pro.setString(7, (String)kruzhokCB.getSelectedItem());
                    stored_pro.setString(8, (String)nationalityCB.getSelectedItem());
                    stored_pro.execute();
                    JOptionPane.showMessageDialog(null, "Сохранить");
                    groupCB.setSelectedItem(null);
                    kruzhokCB.setSelectedItem(null);
                    nationalityCB.setSelectedItem(null);
                }catch(Exception ex){
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
                DefaultCategoryDataset dataset  = new DefaultCategoryDataset();
                dataset.setValue(35, "Marks", "Отсутствует(не посещают)");
                dataset.setValue(20, "Marks", "Chess");
                dataset.setValue(65, "Marks", "Dancing");
                JFreeChart chart  = ChartFactory.createBarChart("Количество посещений кружков ", "Наименование кружков", "Количество посещений в %", dataset, PlotOrientation.VERTICAL, false, true, false);
                CategoryPlot p=chart.getCategoryPlot();
                p.setRangeGridlinePaint(Color.black);
                ChartFrame frame  = new ChartFrame("График", chart);
                frame.setVisible(true);
                frame.setSize(650,550);
            }
        });
        удалитьButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    conn = Main.ConnectDB();
                    stored_pro = conn.prepareCall("{call delete_child (?)}");
                    stored_pro.setString(1, id.getText());
                    stored_pro.execute();
                    JOptionPane.showMessageDialog(null, "Удалить");
                }catch(Exception ex){
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
    }
    private void showMeAllReport(){

        try{
            conn = Main.ConnectDB();
            JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\VICTUS\\Documents\\NetBeansProjects\\kindergarten\\src\\kindergarten\\reportChildrenAll.jrxml");
            String sql = "SELECT    \n" +
                    "     View_children.\"id\" AS View_children_id,\n" +
                    "     View_children.\"FIO\" AS View_children_FIO,\n" +
                    "     View_children.\"dateBorn\" AS View_children_dateborn,\n" +
                    "     View_children.\"adress\" AS View_children_addres,\n" +
                    "     View_children.\"telDad\" AS View_children_tel_dad,\n" +
                    "     View_children.\"telMom\" AS View_children_tel_mom,\n" +
                    "     View_children.\"groups\" AS View_children_groups,\n" +
                    "     View_children.\"kruzhok\" AS View_children_kruzhok,\n" +
                    "     View_children.\"nationality\" AS View_children_nationality\n" +
                    "FROM\n" +
                    "     \"View_children\" View_children";
            JRDesignQuery newQuery = new JRDesignQuery();
            newQuery.setText(sql);
            jasperDesign.setQuery(newQuery);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, conn);
            JasperViewer.viewReport(jasperPrint, false);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void UpdateJTable(){
        try{
            conn = Main.ConnectDB();
            String sql = "SELECT * FROM [View_children]";
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            this.jTable1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
    private void showById(){
        try{
            conn = Main.ConnectDB();
            JasperDesign jasperDesign = JRXmlLoader.load("C:\\Users\\VICTUS\\Documents\\NetBeansProjects\\kindergarten\\src\\kindergarten\\newReport2.jrxml");
            HashMap hm = new HashMap();
            hm.put("param_id3", Integer.parseInt(this.id.getText().toString()));
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hm, conn);
            JasperViewer.viewReport(jasperPrint, false);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void FIOActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
}
