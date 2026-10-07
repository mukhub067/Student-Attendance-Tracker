package com.attendance.view;

import com.attendance.controller.AttendanceController;
import com.attendance.model.Course;
import com.attendance.model.StudentAttendanceReport;
import com.attendance.util.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ReportsPanel extends JPanel {
    private final AttendanceController controller; private final JTextField query=new JTextField(18); private final JComboBox<Course> courses=new JComboBox<>(); private final DefaultTableModel model=new DefaultTableModel(new String[]{"Student code","Student name","Course","Present days","Marked days","Attendance %"},0){public boolean isCellEditable(int r,int c){return false;}}; private final JTable table=new JTable(model);
    public ReportsPanel(AttendanceController controller){this.controller=controller;setLayout(new BorderLayout(12,12));setBackground(UITheme.LIGHT);setBorder(UITheme.padding(20));JPanel top=UITheme.panel(new FlowLayout(FlowLayout.LEFT,10,8));top.add(UITheme.title("Attendance Reports"));top.add(Box.createHorizontalStrut(20));top.add(new JLabel("Student name / code"));top.add(query);top.add(new JLabel("Course"));top.add(courses);JButton search=UITheme.button("Search"),reset=UITheme.button("Reset");top.add(search);top.add(reset);search.addActionListener(e->refresh());reset.addActionListener(e->{query.setText("");courses.setSelectedIndex(0);refresh();});add(top,BorderLayout.NORTH);UITheme.styleTable(table);add(new JScrollPane(table),BorderLayout.CENTER);refreshCourses();refresh();}
    @Override public void setVisible(boolean visible){if(visible){refreshCourses();refresh();}super.setVisible(visible);} private void refreshCourses(){Course current=(Course)courses.getSelectedItem();courses.removeAllItems();courses.addItem(null);for(Course c:controller.getCourses())courses.addItem(c);if(current!=null)courses.setSelectedItem(current);} private void refresh(){model.setRowCount(0);Course c=(Course)courses.getSelectedItem();for(StudentAttendanceReport row:controller.reports(query.getText(),c==null?null:c.getId()))model.addRow(new Object[]{row.studentCode(),row.studentName(),row.courseName(),row.presentDays(),row.markedDays(),String.format("%.1f%%",row.percentage())});}
}
