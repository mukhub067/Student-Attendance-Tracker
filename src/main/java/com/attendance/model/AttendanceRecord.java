package com.attendance.model;

import java.time.LocalDate;
public class AttendanceRecord {
    private final int enrollmentId; private final LocalDate date; private boolean present;
    public AttendanceRecord(int enrollmentId, LocalDate date, boolean present) { this.enrollmentId=enrollmentId; this.date=date; this.present=present; }
    public int getEnrollmentId() { return enrollmentId; } public LocalDate getDate() { return date; } public boolean isPresent() { return present; } public void setPresent(boolean present) { this.present=present; }
}
