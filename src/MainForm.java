import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class MainForm extends JFrame {
    private JButton buttonYasli;
    private JButton buttonMiddle;
    private JButton button1;

    public JPanel panel;
    private JButton buttonSenior;
    private JButton buttonList;
    private JButton buttonEmpl;

    public MainForm() {
        buttonEmpl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

        buttonList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AllChildren form3 = new AllChildren();
                form3.setVisible(true);
                form3.setContentPane(form3.panelchill);
                form3.setTitle("Список всех детей");
                form3.setSize(650, 500);
                form3.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });

        buttonSenior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonMiddle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MiddleG form4 = new MiddleG();
                form4.setVisible(true);
                form4.setContentPane(form4.panel);
                form4.setTitle("Список средней группы");
                form4.setSize(650, 500);
                form4.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });

        buttonSenior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SeniorG form5 = new SeniorG();
                form5.setVisible(true);
                form5.setContentPane(form5.panel);
                form5.setTitle("Список старшей группы");
                form5.setSize(650, 500);
                form5.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JuniorG form5 = new JuniorG();
                form5.setVisible(true);
                form5.setContentPane(form5.panel);
                form5.setTitle("Список младшей группы");
                form5.setSize(650, 500);
                form5.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            }
        });
    }
}
