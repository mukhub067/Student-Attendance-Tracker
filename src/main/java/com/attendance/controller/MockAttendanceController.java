package com.attendance.controller;

import com.attendance.model.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/** In-memory data source. Replace calls here with DAOs during the JDBC milestone. */
public class MockAttendanceController {
    private final List<Student> students = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final List<Enrollment> enrollments = new ArrayList<>();
    private final List<AttendanceRecord> records = new ArrayList<>();
    private int studentSequence=1, courseSequence=1, enrollmentSequence=1;

    public MockAttendanceController() {
        addStudent("S001", "John Doe", "john.doe@example.com"); addStudent("S002", "Jane Smith", "jane.smith@example.com"); addStudent("S003", "Arun Kumar", "arun.kumar@example.com");
        addCourse("JAVA101", "Java Programming", "Core Java and Swing"); addCourse("DB201", "Database Systems", "Relational database concepts");
        enroll(students.get(0).getId(), courses.get(0).getId()); enroll(students.get(1).getId(), courses.get(0).getId()); enroll(students.get(2).getId(), courses.get(1).getId());
        saveAttendance(courses.get(0).getId(), LocalDate.now().minusDays(2), Map.of(students.get(0).getId(), true, students.get(1).getId(), false));
        saveAttendance(courses.get(0).getId(), LocalDate.now().minusDays(1), Map.of(students.get(0).getId(), true, students.get(1).getId(), true));
    }
    public List<Student> getStudents() { return List.copyOf(students); } public List<Course> getCourses() { return List.copyOf(courses); }
    public void addStudent(String code,String name,String email) { validateStudent(code,name,email,null); students.add(new Student(studentSequence++,code.trim(),name.trim(),email.trim())); }
    public void updateStudent(int id,String code,String name,String email) { Student s=findStudent(id); validateStudent(code,name,email,id); s.update(code.trim(),name.trim(),email.trim()); }
    public void deleteStudent(int id) { if(enrollments.stream().anyMatch(e->e.studentId()==id)) throw new IllegalArgumentException("Remove this student's enrollments first."); students.removeIf(s->s.getId()==id); }
    public void addCourse(String code,String name,String description) { validateCourse(code,name,null); courses.add(new Course(courseSequence++,code.trim(),name.trim(),description.trim())); }
    public void updateCourse(int id,String code,String name,String description) { Course c=findCourse(id); validateCourse(code,name,id); c.update(code.trim(),name.trim(),description.trim()); }
    public void deleteCourse(int id) { if(enrollments.stream().anyMatch(e->e.courseId()==id)) throw new IllegalArgumentException("Remove enrollments for this course first."); courses.removeIf(c->c.getId()==id); }
    public List<Enrollment> getEnrollments() { return List.copyOf(enrollments); }
    public void enroll(int studentId,int courseId) { findStudent(studentId); findCourse(courseId); if(enrollments.stream().anyMatch(e->e.studentId()==studentId&&e.courseId()==courseId)) throw new IllegalArgumentException("This student is already enrolled in the course."); enrollments.add(new Enrollment(enrollmentSequence++,studentId,courseId)); }
    public void removeEnrollment(int enrollmentId) { records.removeIf(r->r.getEnrollmentId()==enrollmentId); enrollments.removeIf(e->e.id()==enrollmentId); }
    public List<Student> studentsForCourse(int courseId) { return enrollments.stream().filter(e->e.courseId()==courseId).map(e->findStudent(e.studentId())).toList(); }
    public boolean attendanceFor(int studentId,int courseId,LocalDate date) { return enrollments.stream().filter(e->e.studentId()==studentId&&e.courseId()==courseId).findFirst().flatMap(e->records.stream().filter(r->r.getEnrollmentId()==e.id()&&r.getDate().equals(date)).findFirst()).map(AttendanceRecord::isPresent).orElse(false); }
    public void saveAttendance(int courseId,LocalDate date,Map<Integer,Boolean> presence) { for(Enrollment e:enrollments.stream().filter(x->x.courseId()==courseId).toList()) { boolean value=presence.getOrDefault(e.studentId(),false); AttendanceRecord existing=records.stream().filter(r->r.getEnrollmentId()==e.id()&&r.getDate().equals(date)).findFirst().orElse(null); if(existing==null) records.add(new AttendanceRecord(e.id(),date,value)); else existing.setPresent(value); } }
    public List<ReportRow> reports(String studentQuery,Integer courseId) { String q=studentQuery==null?"":studentQuery.trim().toLowerCase(); List<ReportRow> rows=new ArrayList<>(); for(Enrollment e:enrollments) { Student s=findStudent(e.studentId()); Course c=findCourse(e.courseId()); if(!q.isEmpty()&&!s.getName().toLowerCase().contains(q)&&!s.getCode().toLowerCase().contains(q)) continue; if(courseId!=null&&c.getId()!=courseId) continue; List<AttendanceRecord> matched=records.stream().filter(r->r.getEnrollmentId()==e.id()).toList(); long present=matched.stream().filter(AttendanceRecord::isPresent).count(); double percent=matched.isEmpty()?0:(present*100.0/matched.size()); rows.add(new ReportRow(s.getCode(),s.getName(),c.getName(),present,matched.size(),percent)); } return rows; }
    public Student findStudent(int id) { return students.stream().filter(s->s.getId()==id).findFirst().orElseThrow(()->new IllegalArgumentException("Student not found.")); }
    public Course findCourse(int id) { return courses.stream().filter(c->c.getId()==id).findFirst().orElseThrow(()->new IllegalArgumentException("Course not found.")); }
    private void validateStudent(String code,String name,String email,Integer ignored) { if(code==null||code.isBlank()||name==null||name.isBlank()||email==null||email.isBlank()) throw new IllegalArgumentException("Student code, name, and email are required."); if(students.stream().anyMatch(s->s.getId()!=(ignored==null?-1:ignored)&&(s.getCode().equalsIgnoreCase(code.trim())||s.getEmail().equalsIgnoreCase(email.trim())))) throw new IllegalArgumentException("Student code and email must be unique."); }
    private void validateCourse(String code,String name,Integer ignored) { if(code==null||code.isBlank()||name==null||name.isBlank()) throw new IllegalArgumentException("Course code and name are required."); if(courses.stream().anyMatch(c->c.getId()!=(ignored==null?-1:ignored)&&c.getCode().equalsIgnoreCase(code.trim()))) throw new IllegalArgumentException("Course code must be unique."); }
    public record ReportRow(String studentCode,String studentName,String courseName,long present,long total,double percentage) { }
}
