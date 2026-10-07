package com.attendance.view;

import com.attendance.controller.AttendanceController;
import com.attendance.dao.TeacherDAO;
import com.attendance.model.Teacher;
import com.attendance.util.UITheme;
import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField username=new JTextField(18); private final JPasswordField password=new JPasswordField(18); private final JLabel error=new JLabel(" ");
    public LoginFrame() {
        setTitle("Student Attendance Tracker - Login"); setDefaultCloseOperation(EXIT_ON_CLOSE); setResizable(false);
        JPanel root=UITheme.panel(new GridBagLayout()); root.setBorder(UITheme.padding(28)); GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(7,7,7,7); g.anchor=GridBagConstraints.WEST;
        JLabel heading=UITheme.title("Teacher Login"); g.gridx=0;g.gridy=0;g.gridwidth=2;root.add(heading,g); g.gridwidth=1;
        g.gridy++;root.add(new JLabel("Username"),g);g.gridx=1;root.add(username,g);g.gridx=0;g.gridy++;root.add(new JLabel("Password"),g);g.gridx=1;root.add(password,g);
        error.setForeground(UITheme.DANGER);g.gridx=0;g.gridy++;g.gridwidth=2;root.add(error,g);
        JButton login=UITheme.button("Sign in"); g.gridy++;g.anchor=GridBagConstraints.CENTER;root.add(login,g); login.addActionListener(e -> authenticate()); password.addActionListener(e -> authenticate());
        setContentPane(root); pack(); setLocationRelativeTo(null);
    }
    private void authenticate() { try { Teacher teacher = new TeacherDAO().authenticate(username.getText(), new String(password.getPassword())); if (teacher != null) { dispose(); new DashboardFrame(new AttendanceController(teacher)).setVisible(true); } else error.setText("Invalid username or password."); } catch (Exception ex) { error.setText("Database connection failed. Check configuration."); UITheme.error(this, ex); } }
}
