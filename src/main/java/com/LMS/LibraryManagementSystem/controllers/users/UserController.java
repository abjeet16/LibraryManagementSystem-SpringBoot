package com.LMS.LibraryManagementSystem.controllers.users;

import com.LMS.LibraryManagementSystem.dto.responses.UserProfileDTO;
import com.LMS.LibraryManagementSystem.services.auth.MyCustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/my_profile")
    public ResponseEntity<UserProfileDTO> getMyProfile() {
        // Get the current authenticated user
        MyCustomUserDetails user = (MyCustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Check if the user is authenticated
        if (user == null) {
            return ResponseEntity.status(401).body(null);
        }

        // Map the user details to the UserProfileDTO
        UserProfileDTO userProfile = new UserProfileDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getUserId(),
                user.getUsername(),
                user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())
        );

        // Return the user profile
        return ResponseEntity.ok(userProfile);
    }
}


