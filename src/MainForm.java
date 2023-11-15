import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;

public class MainForm extends JFrame{
    private JButton buttonYasli;
    private JButton buttonMiddle;
    private JButton button1;

    public JPanel panel;
    private JButton buttonSenior;
    private JButton buttonList;
    private JButton buttonEmpl;

    Connection conn;
    ResultSet rs;
    public MainForm() {
        buttonEmpl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //new Employees().setVisible(true);
                Employees form1 = new Employees();
                form1.setVisible(true);
                form1.setContentPane(form1.jpanel);
                form1.setTitle("Сотрудники");
                form1.setSize(600, 450);
                form1.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });
        buttonYasli.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KinderG form2 = new KinderG();
                form2.setVisible(true);
                form2.setContentPane(form2.panel);
                form2.setTitle("Ясли");
                form2.setSize(600, 450);
                form2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });
    }

    /*public static void main(String[] args) {
        MainForm form = new MainForm();
        form.setVisible(true);
        form.setContentPane(form.panel);
        form.setTitle("Kindergarten");
        form.setSize(600, 300);
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }*/

}
