package com.example.historiaclinica.dto;

import com.example.historiaclinica.model.RoleName;
import java.util.Set;

public class UserRegistrationDto {
    private String username;
    private String email;
    private String password;
    private Set<RoleName> roles;

    // Getters y Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleName> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleName> roles) {
        this.roles = roles;
    }
}
