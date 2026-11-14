
package org.example;

public class Assignment {

    private final int id;
    private final String course;
    private final String title;
    private final String dueDate;
    private final boolean completed;

    public Assignment(int id, String course, String title,
                      String dueDate, boolean completed) {
        this.id = id;
        this.course = course;
        this.title = title;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getCourse() {
        return course;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }
}

