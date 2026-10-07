package com.attendance.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Opens Oracle connections from process environment variables; secrets are never stored in source. */
public final class DatabaseManager {
    private DatabaseManager() { }
    public static Connection getConnection() throws SQLException {
        String url = required("ATTENDANCE_DB_URL");
        String user = required("ATTENDANCE_DB_USER");
        String password = required("ATTENDANCE_DB_PASSWORD");
        return DriverManager.getConnection(url, user, password);
    }
    private static String required(String key) {
        String value = System.getenv(key);
        if (value == null || value.isBlank()) throw new IllegalStateException("Missing Windows environment variable: " + key);
        return value;
    }
}
