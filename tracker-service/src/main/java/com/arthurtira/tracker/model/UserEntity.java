package com.arthurtira.tracker.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;

@Entity
@Data
@ToString(exclude = "password")
public class UserEntity extends AbstractEntity {
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

}
