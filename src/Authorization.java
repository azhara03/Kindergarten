import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class Authorization extends JFrame{
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton войтиButton;
    private JButton закрытьButton;
    public JPanel jPanel;
    Connection conn = null;
    ResultSet rs = null;
    CallableStatement stored_pro = null;
public Authorization() {
    войтиButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                conn = Main.ConnectDB();
                stored_pro = conn.prepareCall("{call select_login(?, ?)}");
                stored_pro.setString(1, textField1.getText());
                stored_pro.setString(2, passwordField1.getText());
                rs = stored_pro.executeQuery();
                if(rs.next()){
                    JOptionPane.showMessageDialog(null, "Correct user name and password");
                    rs.close();
                    stored_pro.close();
                    conn.close();
                    MainForm form = new MainForm();
                    form.setVisible(true);
                    form.setContentPane(form.panel);
                    form.setTitle("Kindergarten");
                    form.setSize(600, 300);
                    form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setVisible(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Wrong user name or password");
                }
            }
            catch(Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    });
}
}
