package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageCircularsNotices implements Serializable {
    @javafx.fxml.FXML
    private String circularID;
    private String title;
    private LocalDate issueDate;
    private LocalDate expireDate;
    private String status;

    public ManageCircularsNotices(String circularID, String status, LocalDate expireDate, LocalDate issueDate, String title) {
        this.circularID = circularID;
        this.status = status;
        this.expireDate = expireDate;
        this.issueDate = issueDate;
        this.title = title;
    }

    public String getCircularID() {
        return circularID;
    }

    public void setCircularID(String circularID) {
        this.circularID = circularID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManageCircularsNotices{" +
                "circularID='" + circularID + '\'' +
                ", title='" + title + '\'' +
                ", issueDate=" + issueDate +
                ", expireDate=" + expireDate +
                ", status='" + status + '\'' +
                '}';
    }
}
