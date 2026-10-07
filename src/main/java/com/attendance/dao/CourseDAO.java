package com.attendance.dao;

import com.attendance.model.Course;
import java.sql.*;
import java.util.*;

public class CourseDAO {
    public List<Course> getAllCourses(int teacherId){List<Course> result=new ArrayList<>();String sql="SELECT course_id, course_code, course_name, description FROM courses WHERE teacher_id=? ORDER BY course_name";try(Connection c=DatabaseManager.getConnection();PreparedStatement s=c.prepareStatement(sql)){s.setInt(1,teacherId);try(ResultSet rs=s.executeQuery()){while(rs.next())result.add(new Course(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));}}catch(SQLException ex){throw new DataAccessException("Unable to load courses.",ex);}return result;}
    public void addCourse(Course course,int teacherId){write("INSERT INTO courses(course_code, course_name, description, teacher_id) VALUES (?, ?, ?, ?)",course,teacherId);}
    public void updateCourse(Course course,int teacherId){try(Connection c=DatabaseManager.getConnection();PreparedStatement s=c.prepareStatement("UPDATE courses SET course_code=?, course_name=?, description=? WHERE course_id=? AND teacher_id=?")){s.setString(1,course.getCode());s.setString(2,course.getName());s.setString(3,course.getDescription());s.setInt(4,course.getId());s.setInt(5,teacherId);s.executeUpdate();}catch(SQLException ex){throw new DataAccessException("Unable to update course.",ex);}}
    public void deleteCourse(int id,int teacherId){try(Connection c=DatabaseManager.getConnection();PreparedStatement s=c.prepareStatement("DELETE FROM courses WHERE course_id=? AND teacher_id=?")){s.setInt(1,id);s.setInt(2,teacherId);s.executeUpdate();}catch(SQLException ex){throw new DataAccessException("Unable to delete course with enrollments.",ex);}}
    private void write(String sql,Course course,int teacherId){try(Connection c=DatabaseManager.getConnection();PreparedStatement s=c.prepareStatement(sql)){s.setString(1,course.getCode());s.setString(2,course.getName());s.setString(3,course.getDescription());s.setInt(4,teacherId);s.executeUpdate();}catch(SQLException ex){throw new DataAccessException("Unable to add course.",ex);}}
}
