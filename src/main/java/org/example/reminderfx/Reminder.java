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
    private String hash;

    public Reminder(String description, String date, String time, String priority) {
        this.hash = generateReminderHash();
        this.description = description;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }

    private String generateReminderHash() {
        String data = description + date + time + priority;
        return Utils.generateSHA256Hash(data);
    }

    // Getters and setters for description, date, time, priority, and hash
    public String getHash() {
        return hash;
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
