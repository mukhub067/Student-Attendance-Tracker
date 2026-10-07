-- The password hash below is PBKDF2-HMAC-SHA256 for password "password" with one iteration.
-- Sign in as admin/password, then create a production teacher hash with PasswordUtil.hash().
INSERT INTO teachers(username, password_hash, full_name)
VALUES ('admin', '1:c2FsdA==:Eg+2z/z4syxD5yJSVsT4N6hlSMkszDVICAWYfLcL4Xs=', 'System Administrator');
INSERT INTO students(student_code, full_name, email) VALUES ('S001', 'John Doe', 'john.doe@example.com');
INSERT INTO students(student_code, full_name, email) VALUES ('S002', 'Jane Smith', 'jane.smith@example.com');
INSERT INTO courses(course_code, course_name, description, teacher_id) VALUES ('JAVA101', 'Java Programming', 'Core Java and Swing', 1);
INSERT INTO enrollments(student_id, course_id) VALUES (1, 1);
INSERT INTO enrollments(student_id, course_id) VALUES (2, 1);
COMMIT;
