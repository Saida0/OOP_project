package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageCircularsForBanks implements Serializable {
    @javafx.fxml.FXML
    private String circularID;
    private String title;
    private LocalDate issueDate;
    private LocalDate expireDate;
    private String status;

    public ManageCircularsForBanks(String circularID, String title, LocalDate issueDate, LocalDate expireDate, String status) {
        this.circularID = circularID;
        this.title = title;
        this.issueDate = issueDate;
        this.expireDate = expireDate;
        this.status = status;
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
        return "ManageCircularsForBanks{" +
                "circularID='" + circularID + '\'' +
                ", title='" + title + '\'' +
                ", issueDate=" + issueDate +
                ", expireDate=" + expireDate +
                ", status='" + status + '\'' +
                '}';
    }
}
