package org.example.reminderfx;

import javafx.fxml.FXML;

public class Reminder {
    @FXML
    private String description;
    @FXML
    private String date;
    @FXML
    private String time;
    @FXML
    private String priority;

    public Reminder(String description, String date, String time, String priority) {
        this.description = description;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    // Getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

}
