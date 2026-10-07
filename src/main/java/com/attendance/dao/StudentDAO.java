package com.attendance.dao;

import com.attendance.model.Student;
import java.sql.*;
import java.util.*;

public class StudentDAO {
    public List<Student> getAllStudents() { return query("SELECT student_id, student_code, full_name, email FROM students ORDER BY full_name", null); }
    public List<Student> searchStudentsByName(String name) { return query("SELECT student_id, student_code, full_name, email FROM students WHERE LOWER(full_name) LIKE ? OR LOWER(student_code) LIKE ? ORDER BY full_name", statement -> { String like="%"+name.trim().toLowerCase()+"%"; statement.setString(1,like);statement.setString(2,like); }); }
    public void addStudent(String code,String name,String email) { execute("INSERT INTO students(student_code, full_name, email) VALUES (?, ?, ?)", s->{s.setString(1,code.trim());s.setString(2,name.trim());s.setString(3,email.trim());}); }
    public void updateStudent(int id,String code,String name,String email) { execute("UPDATE students SET student_code=?, full_name=?, email=? WHERE student_id=?", s->{s.setString(1,code.trim());s.setString(2,name.trim());s.setString(3,email.trim());s.setInt(4,id);}); }
    public void deleteStudent(int id) { execute("DELETE FROM students WHERE student_id=?", s->s.setInt(1,id)); }
    private List<Student> query(String sql, Binder binder) { List<Student> result=new ArrayList<>();try(Connection c=DatabaseManager.getConnection();PreparedStatement s=c.prepareStatement(sql)){if(binder!=null)binder.bind(s);try(ResultSet rs=s.executeQuery()){while(rs.next())result.add(new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));}}catch(SQLException ex){throw new DataAccessException("Unable to load students.",ex);}return result; }
    private void execute(String sql,Binder binder){try(Connection c=DatabaseManager.getConnection();PreparedStatement s=c.prepareStatement(sql)){binder.bind(s);s.executeUpdate();}catch(SQLException ex){throw new DataAccessException("Student operation failed. Check duplicate values or related enrollments.",ex);}}
    @FunctionalInterface private interface Binder { void bind(PreparedStatement statement) throws SQLException; }
}
