package com.attendance.dao;

import com.attendance.model.Teacher;
import com.attendance.util.PasswordUtil;
import java.sql.*;

public class TeacherDAO {
    public Teacher authenticate(String username, String password) {
        String sql = "SELECT teacher_id, username, password_hash, full_name FROM teachers WHERE username = ?";
        try (Connection connection = DatabaseManager.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username.trim());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next() && PasswordUtil.matches(password, rs.getString("password_hash"))) return new Teacher(rs.getInt("teacher_id"), rs.getString("username"), rs.getString("full_name"));
                return null;
            }
        } catch (SQLException ex) { throw new DataAccessException("Unable to authenticate with the database.", ex); }
    }
}
