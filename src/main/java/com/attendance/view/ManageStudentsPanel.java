package com.attendance.view;

import com.attendance.controller.MockAttendanceController;
import com.attendance.model.Student;
import com.attendance.util.UITheme;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ManageStudentsPanel extends JPanel {
    private final MockAttendanceController controller; private final DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Code","Name","Email"},0){public boolean isCellEditable(int r,int c){return false;}};
    private final JTable table=new JTable(model); private final JTextField code=new JTextField(10),name=new JTextField(18),email=new JTextField(20);
    public ManageStudentsPanel(MockAttendanceController controller) { this.controller=controller; setLayout(new BorderLayout(12,12));setBackground(UITheme.LIGHT);setBorder(UITheme.padding(20));add(header("Manage Students"),BorderLayout.NORTH);UITheme.styleTable(table);table.getSelectionModel().addListSelectionListener(e->{if(!e.getValueIsAdjusting()&&table.getSelectedRow()>=0) load();});add(new JScrollPane(table),BorderLayout.CENTER);add(form(),BorderLayout.SOUTH);refresh(); }
    private JPanel header(String title) { JPanel p=UITheme.panel(new BorderLayout());p.add(UITheme.title(title),BorderLayout.WEST);return p; }
    private JPanel form() {
        JPanel panel = UITheme.panel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.add(new JLabel("Student code"));
        panel.add(code);
        panel.add(new JLabel("Name"));
        panel.add(name);
        panel.add(new JLabel("Email"));
        panel.add(email);

        JButton addButton = UITheme.button("Add");
        JButton updateButton = UITheme.button("Update");
        JButton deleteButton = UITheme.dangerButton("Delete");
        JButton clearButton = UITheme.button("Clear");
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(clearButton);

        addButton.addActionListener(event -> addStudent());
        updateButton.addActionListener(event -> updateStudent());
        deleteButton.addActionListener(event -> deleteStudent());
        clearButton.addActionListener(event -> clearForm());
        return panel;
    }
    private void run(Runnable action) { try { action.run();refresh();clearForm(); } catch(Exception ex){UITheme.error(this,ex);} }
    private void addStudent() { run(() -> controller.addStudent(code.getText(), name.getText(), email.getText())); }
    private void updateStudent() { run(() -> controller.updateStudent(selectedId(), code.getText(), name.getText(), email.getText())); }
    private void deleteStudent() { run(() -> controller.deleteStudent(selectedId())); }
    private int selectedId(){int r=table.getSelectedRow();if(r<0)throw new IllegalArgumentException("Select a student first.");return (Integer)model.getValueAt(r,0);}
    private void load(){int r=table.getSelectedRow();code.setText((String)model.getValueAt(r,1));name.setText((String)model.getValueAt(r,2));email.setText((String)model.getValueAt(r,3));}
    private void clearForm(){table.clearSelection();code.setText("");name.setText("");email.setText("");}
    private void refresh(){model.setRowCount(0);for(Student s:controller.getStudents())model.addRow(new Object[]{s.getId(),s.getCode(),s.getName(),s.getEmail()});}
}
