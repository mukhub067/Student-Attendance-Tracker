package com.attendance.model;

public class Student {
    private final int id;
    private String code, name, email;
    public Student(int id, String code, String name, String email) { this.id=id; this.code=code; this.name=name; this.email=email; }
    public int getId() { return id; } public String getCode() { return code; } public String getName() { return name; } public String getEmail() { return email; }
    public void update(String code, String name, String email) { this.code=code; this.name=name; this.email=email; }
    @Override public String toString() { return code + " - " + name; }
}
