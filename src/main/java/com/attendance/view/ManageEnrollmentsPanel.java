package com.attendance.view;

import com.attendance.controller.AttendanceController;
import com.attendance.model.*;
import com.attendance.util.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageEnrollmentsPanel extends JPanel {
    private final AttendanceController controller; private final JComboBox<Student> students=new JComboBox<>(); private final JComboBox<Course> courses=new JComboBox<>();
    private final DefaultTableModel model=new DefaultTableModel(new String[]{"Enrollment ID","Student","Course"},0){public boolean isCellEditable(int r,int c){return false;}}; private final JTable table=new JTable(model);
    public ManageEnrollmentsPanel(AttendanceController controller){this.controller=controller;setLayout(new BorderLayout(12,12));setBackground(UITheme.LIGHT);setBorder(UITheme.padding(20));JPanel h=UITheme.panel(new BorderLayout());h.add(UITheme.title("Manage Enrollments"),BorderLayout.WEST);add(h,BorderLayout.NORTH);UITheme.styleTable(table);add(new JScrollPane(table),BorderLayout.CENTER);JPanel controls=UITheme.panel(new FlowLayout(FlowLayout.LEFT,10,10));controls.add(new JLabel("Student"));controls.add(students);controls.add(new JLabel("Course"));controls.add(courses);JButton enroll=UITheme.button("Enroll Student"),remove=UITheme.dangerButton("Remove Selected");controls.add(enroll);controls.add(remove);enroll.addActionListener(e->{try{Student s=(Student)students.getSelectedItem();Course c=(Course)courses.getSelectedItem();if(s==null||c==null)throw new IllegalArgumentException("Create a student and course first.");controller.enroll(s.getId(),c.getId());refresh();}catch(Exception ex){UITheme.error(this,ex);}});remove.addActionListener(e->{try{int r=table.getSelectedRow();if(r<0)throw new IllegalArgumentException("Select an enrollment first.");controller.removeEnrollment((Integer)model.getValueAt(r,0));refresh();}catch(Exception ex){UITheme.error(this,ex);}});add(controls,BorderLayout.SOUTH);refresh();}
    @Override public void setVisible(boolean visible){if(visible)refresh();super.setVisible(visible);} private void refresh(){students.removeAllItems();courses.removeAllItems();for(Student s:controller.getStudents())students.addItem(s);for(Course c:controller.getCourses())courses.addItem(c);model.setRowCount(0);for(Enrollment e:controller.getEnrollments())model.addRow(new Object[]{e.id(),controller.findStudent(e.studentId()).getName(),controller.findCourse(e.courseId()).getName()});}
}
