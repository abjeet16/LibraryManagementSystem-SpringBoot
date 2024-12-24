package com.LMS.LibraryManagementSystem.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserProfileDTO {

    private String firstName;
    private String lastName;
    private Integer userId;
    private String username; // Email
    private List<String> authorities; // Authorities (roles)
}

