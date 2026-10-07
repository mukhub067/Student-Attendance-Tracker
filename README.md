# Student Attendance Tracker

A Java 17 Swing frontend following MVC principles. It currently uses an in-memory mock controller; no JDBC code runs yet.

## Run the application

Install JDK 17 and Maven, then run:

```powershell
mvn compile exec:java
```

Sign in with username `admin` and password `password`.

## Current application behavior

- Manage students and courses with in-memory add, update, and delete actions.
- Enroll students in courses before marking attendance.
- Select a course and ISO date (`YYYY-MM-DD`), then save present/absent values.
- Search attendance reports by student name/code and course.
- Closing the application resets mock data; that is intentional until JDBC is implemented.

## Oracle XE 21c setup

Oracle XE normally exposes its pluggable database as `XEPDB1` on port `1521`. In SQL*Plus or SQL Developer, connect as a system administrator, then create an application schema:

```sql
CREATE USER attendance_app IDENTIFIED BY ChooseAStrongPassword;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE PROCEDURE TO attendance_app;
ALTER USER attendance_app QUOTA UNLIMITED ON USERS;
```

Reconnect as `attendance_app` to `localhost:1521/XEPDB1`, then run [database/oracle-schema.sql](database/oracle-schema.sql). In SQL Developer, open the script and use **Run Script**. The script creates `STUDENTS`, `COURSES`, `ENROLLMENTS`, and `ATTENDANCE` with primary keys, foreign keys, unique constraints, and valid status checks.

When JDBC is added, use the Oracle driver plus a connection string in this form:

```text
jdbc:oracle:thin:@//localhost:1521/XEPDB1
```

Keep database URL, user, and password outside committed Java source (for example, environment variables or an ignored local properties file).
