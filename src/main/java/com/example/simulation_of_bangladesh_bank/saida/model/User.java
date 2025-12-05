package com.example.simulation_of_bangladesh_bank.saida.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * User model class for authentication and user management.
 * Implements Serializable for binary file storage.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String id;
    private String username;
    private String password;
    private String fullName;
    private String role; // "Commercial Bank Manager" or "Ministry of Finance Representative"
    private String email;
    private String phone;
    private String department;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    // Constructors
    public User() {
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    public User(String id, String username, String password, String fullName, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
    }

    public User(String id, String username, String password, String fullName, 
                String role, String email, String phone, String department) {
        this(id, username, password, fullName, role);
        this.email = email;
        this.phone = phone;
        this.department = department;
    }

    // Authentication method
    public boolean authenticate(String inputPassword) {
        return this.password != null && this.password.equals(inputPassword);
    }

    public void recordLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}

