# Student Attendance Tracker

A Java 17 Swing application following MVC principles, backed by Oracle XE through JDBC.

## Run the application

Install JDK 17 and Maven, then run:

```powershell
mvn compile exec:java
```

After running the seed script, sign in with username `admin` and password `password`.

## Current application behavior

- Manage students and teacher-owned courses with Oracle CRUD actions.
- Enroll students in courses before marking attendance.
- Select a course and ISO date (`YYYY-MM-DD`), then save present/absent values.
- Search attendance reports by student name/code and course.
- Data persists in Oracle; the mock controller is retained only as reference code.

## Oracle XE 21c setup

Oracle XE normally exposes its pluggable database as `XEPDB1` on port `1521`. In SQL*Plus or SQL Developer, connect as a system administrator, then create an application schema:

```sql
CREATE USER attendance_app IDENTIFIED BY ChooseAStrongPassword;
GRANT CREATE SESSION, CREATE TABLE, CREATE SEQUENCE, CREATE PROCEDURE TO attendance_app;
ALTER USER attendance_app QUOTA UNLIMITED ON USERS;
```

Reconnect as `attendance_app` to `localhost:1521/XEPDB1`. If your earlier test tables still exist and contain no data you need, first run [database/reset-jdbc-development-schema.sql](database/reset-jdbc-development-schema.sql); it permanently deletes those application tables. Then run [database/oracle-jdbc-schema.sql](database/oracle-jdbc-schema.sql) followed by [database/oracle-jdbc-seed.sql](database/oracle-jdbc-seed.sql). In SQL Developer, open each script and use **Run Script**. These scripts create the normalized `TEACHERS`, `STUDENTS`, `COURSES`, `ENROLLMENTS`, and `ATTENDANCE` tables.

When JDBC is added, use the Oracle driver plus a connection string in this form:

```text
jdbc:oracle:thin:@//localhost:1521/XEPDB1
```

Set the database values as Windows environment variables before launching the app:

```powershell
setx ATTENDANCE_DB_URL "jdbc:oracle:thin:@//localhost:1521/XEPDB1"
setx ATTENDANCE_DB_USER "attendance_app"
setx ATTENDANCE_DB_PASSWORD "your_database_password"
```

Open a new PowerShell window after using `setx`, then launch with `mvn clean compile exec:java`.
