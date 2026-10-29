package com.attendance.view;

import com.attendance.controller.AttendanceController;
import com.attendance.model.*;
import com.attendance.util.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MarkAttendancePanel extends JPanel {
    private final AttendanceController controller; private final JTextField date=new JTextField(LocalDate.now().toString(),11); private final JComboBox<Course> courses=new JComboBox<>();
    private final DefaultTableModel model=new DefaultTableModel(new String[]{"Student ID","Student code","Student name","Present"},0){public Class<?> getColumnClass(int c){return c==3?Boolean.class:String.class;}public boolean isCellEditable(int r,int c){return c==3;}}; private final JTable table=new JTable(model);
    private final JLabel status=new JLabel("Select a course and load students. Tick Present for each student who attended.");
    public MarkAttendancePanel(AttendanceController controller){this.controller=controller;setLayout(new BorderLayout(12,12));setBackground(UITheme.LIGHT);setBorder(UITheme.padding(20));JPanel top=UITheme.panel(new FlowLayout(FlowLayout.LEFT,10,8));top.add(UITheme.title("Mark Attendance"));top.add(Box.createHorizontalStrut(25));top.add(new JLabel("Date (YYYY-MM-DD)"));top.add(date);top.add(new JLabel("Course"));top.add(courses);JButton load=UITheme.secondaryButton("Load Students"),save=UITheme.successButton("Save Attendance");top.add(load);top.add(save);load.addActionListener(e->loadRows());save.addActionListener(e->save());JPanel north=UITheme.panel(new BorderLayout(0,6));north.add(top,BorderLayout.NORTH);status.setForeground(UITheme.TEXT);status.setBorder(UITheme.padding(4));north.add(status,BorderLayout.SOUTH);add(north,BorderLayout.NORTH);UITheme.styleTable(table);table.getColumnModel().getColumn(0).setMinWidth(0);table.getColumnModel().getColumn(0).setMaxWidth(0);add(new JScrollPane(table),BorderLayout.CENTER);refreshCourses();}
    @Override public void setVisible(boolean visible){if(visible)refreshCourses();super.setVisible(visible);} private void refreshCourses(){Course chosen=(Course)courses.getSelectedItem();courses.removeAllItems();for(Course c:controller.getCourses())courses.addItem(c);if(chosen!=null)courses.setSelectedItem(chosen);}
    private LocalDate selectedDate(){try{return LocalDate.parse(date.getText().trim());}catch(DateTimeParseException ex){throw new IllegalArgumentException("Use date format YYYY-MM-DD.");}} private Course selectedCourse(){Course c=(Course)courses.getSelectedItem();if(c==null)throw new IllegalArgumentException("Select a course.");return c;}
    private void loadRows(){try{Course c=selectedCourse();LocalDate d=selectedDate();model.setRowCount(0);for(Student s:controller.studentsForCourse(c.getId()))model.addRow(new Object[]{s.getId(),s.getCode(),s.getName(),controller.attendanceFor(s.getId(),c.getId(),d)});if(model.getRowCount()==0){status.setText("No students are enrolled in this course.");JOptionPane.showMessageDialog(this,"No students are enrolled in this course.","No enrollments",JOptionPane.INFORMATION_MESSAGE);}else updateStatus("Loaded");}catch(Exception ex){UITheme.error(this,ex);}}
    private void save(){try{Course c=selectedCourse();LocalDate d=selectedDate();if(model.getRowCount()==0)throw new IllegalArgumentException("Load enrolled students before saving.");Map<Integer,Boolean> presence=new HashMap<>();for(int r=0;r<model.getRowCount();r++)presence.put((Integer)model.getValueAt(r,0),Boolean.TRUE.equals(model.getValueAt(r,3)));controller.saveAttendance(c.getId(),d,presence);loadRows();int present=(int)presence.values().stream().filter(Boolean::booleanValue).count();int absent=presence.size()-present;status.setText("Saved for "+d+": "+present+" present and "+absent+" absent. Checked boxes confirm students marked present.");JOptionPane.showMessageDialog(this,"Attendance saved: "+present+" present, "+absent+" absent.","Attendance saved",JOptionPane.INFORMATION_MESSAGE);}catch(Exception ex){UITheme.error(this,ex);}}
    private void updateStatus(String action){int present=0;for(int row=0;row<model.getRowCount();row++)if(Boolean.TRUE.equals(model.getValueAt(row,3)))present++;status.setText(action+" "+model.getRowCount()+" students: "+present+" present and "+(model.getRowCount()-present)+" absent. A checked box means Present.");}
}
