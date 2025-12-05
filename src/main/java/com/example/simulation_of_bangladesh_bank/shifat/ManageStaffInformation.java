package com.example.simulation_of_bangladesh_bank.shifat;

import java.io.Serializable;
import java.time.LocalDate;

public class ManageStaffInformation implements Serializable {
    @javafx.fxml.FXML
    private String employeeID;
    private String name;
    private String department ;
    private String position ;
    private String salary ;
    private String status;

    public ManageStaffInformation(String employeeID, String status, String salary, String position, String department, String name) {
        this.employeeID = employeeID;
        this.status = status;
        this.salary = salary;
        this.position = position;
        this.department = department;
        this.name = name;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ManageStaffInformation{" +
                "employeeID='" + employeeID + '\'' +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", position='" + position + '\'' +
                ", salary='" + salary + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
