package com.example.firebase;

public class Course {
    private String title;
    private String instructor;
    private String description;

    public Course() {
        // Default constructor required for Firebase
    }

    public Course(String title, String instructor, String description) {
        this.title = title;
        this.instructor = instructor;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
