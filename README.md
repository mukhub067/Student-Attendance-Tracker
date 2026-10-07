# Student Attendance Tracker

A Java Swing desktop application for teachers to manage students, courses, enrollments, and daily attendance. The application follows an MVC-style structure and persists data in Oracle Database XE through JDBC.

## Features

- Teacher login backed by Oracle Database.
- Student CRUD: add, update, delete, and list student records.
- Teacher-owned course CRUD.
- Student-to-course enrollment management.
- Daily present/absent attendance marking.
- Attendance upsert: saving the same student/course/date updates the existing record rather than inserting a duplicate.
- Searchable attendance reports with calculated percentages.
- Native system look-and-feel Java Swing interface.

## Technology Stack

- Java 17
- Java Swing
- Maven
- Oracle Database XE 21c
- Oracle JDBC (`ojdbc11`)

## Project Structure

```text
src/main/java/com/attendance/
├── controller/  Application workflow layer
├── dao/         JDBC data-access objects
├── main/        Application entry point
├── model/       Domain models
├── util/        UI and password utilities
└── view/        Swing frames and panels

database/
├── oracle-jdbc-schema.sql  Normalized database schema
└── oracle-jdbc-seed.sql    Demonstration teacher and data
```

## Database Design

The application uses five normalized tables:

| Table | Purpose |
| --- | --- |
| `TEACHERS` | Teacher credentials and profile data |
| `STUDENTS` | Student master records |
| `COURSES` | Courses owned by a teacher |
| `ENROLLMENTS` | Student-to-course many-to-many relationship |
| `ATTENDANCE` | One present/absent status per enrollment and date |

The unique constraint on `(enrollment_id, attendance_date)` prevents duplicate daily attendance entries.

## Prerequisites

- JDK 17 or newer
- Apache Maven 3.9 or newer
- Oracle Database XE running locally
- Oracle SQL Developer (recommended for running setup scripts)

## Database Setup

1. Connect to your Oracle database using the same account the application will use.
2. Run [database/oracle-jdbc-schema.sql](database/oracle-jdbc-schema.sql).
3. Run [database/oracle-jdbc-seed.sql](database/oracle-jdbc-seed.sql).

The seed script creates the initial application account:

```text
Application username: admin
Application password: password
```

> Change the seed account password before deploying or sharing a real application instance.

## Configure the Database Connection

Set these Windows environment variables. Replace the database username and password with your own Oracle account values.

```powershell
setx ATTENDANCE_DB_URL "jdbc:oracle:thin:@//localhost:1521/XEPDB1"
setx ATTENDANCE_DB_USER "SYSTEM"
setx ATTENDANCE_DB_PASSWORD "your_oracle_password"
```

Close and reopen VS Code or PowerShell after using `setx`. For a current terminal session only, use:

```powershell
$env:ATTENDANCE_DB_URL = 'jdbc:oracle:thin:@//localhost:1521/XEPDB1'
$env:ATTENDANCE_DB_USER = 'SYSTEM'
$env:ATTENDANCE_DB_PASSWORD = 'your_oracle_password'
```

Never commit database passwords or other credentials to this repository.

## Run the Application

From the project root:

```powershell
mvn clean compile exec:java
```

## Usage Flow

1. Sign in as a teacher.
2. Add students and courses.
3. Enroll students in a course.
4. Select a course and date, then save attendance.
5. Open Reports to search and view attendance percentages.

## Security Notes

- Database queries use prepared statements.
- JDBC resources use try-with-resources.
- Teacher passwords are verified using PBKDF2 hashes.
- Database credentials are read from environment variables, not Java source files.

## License

This project is available under the [MIT License](LICENSE).
