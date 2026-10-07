package com.attendance.view;

import com.attendance.controller.AttendanceController;
import com.attendance.model.Course;
import com.attendance.util.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageCoursesPanel extends JPanel {
    private final AttendanceController controller; private final DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Code","Course name","Description"},0){public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table=new JTable(model); private final JTextField code=new JTextField(10),name=new JTextField(18),description=new JTextField(23);
    public ManageCoursesPanel(AttendanceController controller) { this.controller=controller;setLayout(new BorderLayout(12,12));setBackground(UITheme.LIGHT);setBorder(UITheme.padding(20));JPanel h=UITheme.panel(new BorderLayout());h.add(UITheme.title("Manage Courses"),BorderLayout.WEST);add(h,BorderLayout.NORTH);UITheme.styleTable(table);table.getSelectionModel().addListSelectionListener(e->{if(!e.getValueIsAdjusting()&&table.getSelectedRow()>=0)load();});add(new JScrollPane(table),BorderLayout.CENTER);add(form(),BorderLayout.SOUTH);refresh(); }
    private JPanel form() {
        JPanel panel = UITheme.panel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(new JLabel("Course code"));
        panel.add(code);
        panel.add(new JLabel("Name"));
        panel.add(name);
        panel.add(new JLabel("Description"));
        panel.add(description);

        JButton addButton = UITheme.button("Add");
        JButton updateButton = UITheme.button("Update");
        JButton deleteButton = UITheme.dangerButton("Delete");
        JButton clearButton = UITheme.button("Clear");
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(clearButton);

        addButton.addActionListener(event -> addCourse());
        updateButton.addActionListener(event -> updateCourse());
        deleteButton.addActionListener(event -> deleteCourse());
        clearButton.addActionListener(event -> clearForm());
        return panel;
    }
    private void run(Runnable a){try{a.run();refresh();clearForm();}catch(Exception ex){UITheme.error(this,ex);}}
    private void addCourse() { run(() -> controller.addCourse(code.getText(), name.getText(), description.getText())); }
    private void updateCourse() { run(() -> controller.updateCourse(selectedId(), code.getText(), name.getText(), description.getText())); }
    private void deleteCourse() { run(() -> controller.deleteCourse(selectedId())); }
    private int selectedId(){int r=table.getSelectedRow();if(r<0)throw new IllegalArgumentException("Select a course first.");return (Integer)model.getValueAt(r,0);}
    private void load(){int r=table.getSelectedRow();code.setText((String)model.getValueAt(r,1));name.setText((String)model.getValueAt(r,2));description.setText((String)model.getValueAt(r,3));}
    private void clearForm(){table.clearSelection();code.setText("");name.setText("");description.setText("");}
    private void refresh(){model.setRowCount(0);for(Course c:controller.getCourses())model.addRow(new Object[]{c.getId(),c.getCode(),c.getName(),c.getDescription()});}
}
