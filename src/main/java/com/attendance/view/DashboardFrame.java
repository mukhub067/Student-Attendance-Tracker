package com.attendance.view;

import com.attendance.controller.AttendanceController;
import com.attendance.util.UITheme;
import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private final CardLayout cards=new CardLayout(); private final JPanel content=new JPanel(cards);
    private final AttendanceController controller;
    public DashboardFrame(AttendanceController controller) {
        this.controller=controller;
        setTitle("Student Attendance Tracker"); setDefaultCloseOperation(EXIT_ON_CLOSE); setSize(1120,680); setMinimumSize(new Dimension(900,550)); setLocationRelativeTo(null); setLayout(new BorderLayout());
        content.add(new ManageStudentsPanel(controller),"students"); content.add(new ManageCoursesPanel(controller),"courses"); content.add(new ManageEnrollmentsPanel(controller),"enrollments"); content.add(new MarkAttendancePanel(controller),"attendance"); content.add(new ReportsPanel(controller),"reports");
        add(sidebar(),BorderLayout.WEST); add(content,BorderLayout.CENTER); cards.show(content,"students");
    }
    private JPanel sidebar() { JPanel p=new JPanel(); p.setBackground(UITheme.NAVY);p.setBorder(UITheme.padding(14));p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS)); JLabel brand=new JLabel("ATTENDANCE");brand.setFont(UITheme.HEADING);brand.setForeground(Color.WHITE);brand.setAlignmentX(Component.LEFT_ALIGNMENT);p.add(brand);p.add(Box.createVerticalStrut(25));
        addNav(p,"Manage Students","students");addNav(p,"Manage Courses","courses");addNav(p,"Manage Enrollments","enrollments");addNav(p,"Mark Attendance","attendance");addNav(p,"Reports","reports");p.add(Box.createVerticalGlue());JButton logout=navButton("Logout");logout.addActionListener(e->{dispose();new LoginFrame().setVisible(true);});p.add(logout);return p; }
    private void addNav(JPanel p,String text,String card) { JButton b=navButton(text);b.addActionListener(e->cards.show(content,card));p.add(b);p.add(Box.createVerticalStrut(6)); }
    private JButton navButton(String text) { JButton b=new JButton(text);b.setMaximumSize(new Dimension(190,38));b.setAlignmentX(Component.LEFT_ALIGNMENT);b.setHorizontalAlignment(SwingConstants.LEFT);b.setForeground(Color.WHITE);b.setBackground(UITheme.NAVY);b.setBorder(UITheme.padding(9));b.setFocusPainted(false);return b; }
}
