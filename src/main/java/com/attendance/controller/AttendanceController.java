package com.attendance.controller;

import com.attendance.dao.*;
import com.attendance.model.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.*;

/** Application facade: keeps Swing components independent from JDBC details. */
public class AttendanceController {
    private static final Logger LOG=Logger.getLogger(AttendanceController.class.getName());
    private final Teacher teacher; private final StudentDAO students=new StudentDAO(); private final CourseDAO courses=new CourseDAO(); private final EnrollmentDAO enrollments=new EnrollmentDAO(); private final AttendanceDAO attendance=new AttendanceDAO();
    public AttendanceController(Teacher teacher){this.teacher=teacher;} public Teacher getTeacher(){return teacher;}
    public List<Student> getStudents(){return call(students::getAllStudents);} public void addStudent(String c,String n,String e){call(()->{students.addStudent(c,n,e);return null;});} public void updateStudent(int id,String c,String n,String e){call(()->{students.updateStudent(id,c,n,e);return null;});} public void deleteStudent(int id){call(()->{students.deleteStudent(id);return null;});}
    public List<Course> getCourses(){return call(()->courses.getAllCourses(teacher.id()));} public void addCourse(String c,String n,String d){call(()->{courses.addCourse(new Course(0,c,n,d),teacher.id());return null;});} public void updateCourse(int id,String c,String n,String d){call(()->{courses.updateCourse(new Course(id,c,n,d),teacher.id());return null;});} public void deleteCourse(int id){call(()->{courses.deleteCourse(id,teacher.id());return null;});}
    public List<Enrollment> getEnrollments(){return call(()->enrollments.getEnrollments(teacher.id()));} public void enroll(int studentId,int courseId){call(()->{enrollments.enroll(studentId,courseId,teacher.id());return null;});} public void removeEnrollment(int id){call(()->{enrollments.remove(id,teacher.id());return null;});}
    public List<Student> studentsForCourse(int id){return call(()->enrollments.studentsForCourse(id,teacher.id()));} public boolean attendanceFor(int studentId,int courseId,LocalDate date){return call(()->attendance.getAttendanceByDateAndCourse(date,courseId,teacher.id()).getOrDefault(studentId,false));} public void saveAttendance(int courseId,LocalDate date,Map<Integer,Boolean> values){for(Enrollment e:getEnrollments())if(e.courseId()==courseId)call(()->{attendance.markAttendance(e.id(),date,values.getOrDefault(e.studentId(),false),teacher.id());return null;});}
    public List<StudentAttendanceReport> reports(String q,Integer courseId){return call(()->attendance.getStudentAttendanceReport(q,courseId,teacher.id()));}
    public Student findStudent(int id){return getStudents().stream().filter(s->s.getId()==id).findFirst().orElseThrow(()->new IllegalArgumentException("Student not found."));} public Course findCourse(int id){return getCourses().stream().filter(c->c.getId()==id).findFirst().orElseThrow(()->new IllegalArgumentException("Course not found."));}
    private <T>T call(java.util.concurrent.Callable<T> work){try{return work.call();}catch(DataAccessException ex){LOG.log(Level.SEVERE,ex.getMessage(),ex);throw ex;}catch(Exception ex){throw new IllegalStateException(ex);}}
}
