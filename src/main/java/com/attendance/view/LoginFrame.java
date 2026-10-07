package com.attendance.view;

import com.attendance.controller.MockAttendanceController;
import com.attendance.util.UITheme;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField username=new JTextField(18); private final JPasswordField password=new JPasswordField(18); private final JLabel error=new JLabel(" ");
    public LoginFrame(MockAttendanceController controller) {
        setTitle("Student Attendance Tracker - Login"); setDefaultCloseOperation(EXIT_ON_CLOSE); setResizable(false);
        JPanel root=UITheme.panel(new GridBagLayout()); root.setBorder(UITheme.padding(28)); GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(7,7,7,7); g.anchor=GridBagConstraints.WEST;
        JLabel heading=UITheme.title("Teacher Login"); g.gridx=0;g.gridy=0;g.gridwidth=2;root.add(heading,g); g.gridwidth=1;
        g.gridy++;root.add(new JLabel("Username"),g);g.gridx=1;root.add(username,g);g.gridx=0;g.gridy++;root.add(new JLabel("Password"),g);g.gridx=1;root.add(password,g);
        error.setForeground(UITheme.DANGER);g.gridx=0;g.gridy++;g.gridwidth=2;root.add(error,g);
        JButton login=UITheme.button("Sign in"); g.gridy++;g.anchor=GridBagConstraints.CENTER;root.add(login,g); login.addActionListener(e -> authenticate(controller)); password.addActionListener(e -> authenticate(controller));
        setContentPane(root); pack(); setLocationRelativeTo(null);
    }
    private void authenticate(MockAttendanceController controller) { if("admin".equals(username.getText().trim())&&"password".equals(new String(password.getPassword()))) { dispose(); new DashboardFrame(controller).setVisible(true); } else error.setText("Invalid username or password."); }
}
