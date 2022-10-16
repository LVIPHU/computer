package com.example.servercomputer.dto;

import java.util.HashSet;
import java.util.Set;

public class SignupRequest {
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();

    public SignupRequest() {
    }

    public SignupRequest(String email, String password) {
        this.email = email;
        this.password = password;
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

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
