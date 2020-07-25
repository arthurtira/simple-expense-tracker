package com.arthurtira.tracker.dto;

import lombok.Data;

@Data
public class UserEntityDto {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
