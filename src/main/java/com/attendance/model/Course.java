package com.attendance.model;

public class Course {
    private final int id;
    private String code, name, description;
    public Course(int id, String code, String name, String description) { this.id=id; this.code=code; this.name=name; this.description=description; }
    public int getId() { return id; } public String getCode() { return code; } public String getName() { return name; } public String getDescription() { return description; }
    public void update(String code, String name, String description) { this.code=code; this.name=name; this.description=description; }
    @Override public String toString() { return code + " - " + name; }
}
