package com.attendance.model;

public record StudentAttendanceReport(String studentCode, String studentName, String courseName,
                                      long presentDays, long markedDays, double percentage) { }
