package com.attendance.main;

import com.attendance.controller.MockAttendanceController;
import com.attendance.view.LoginFrame;
import javax.swing.*;

public final class MainApplication {
    private MainApplication() { }
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) { }
        SwingUtilities.invokeLater(() -> new LoginFrame(new MockAttendanceController()).setVisible(true));
    }
}
