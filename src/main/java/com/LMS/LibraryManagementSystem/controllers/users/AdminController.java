package com.LMS.LibraryManagementSystem.controllers.users;


import com.LMS.LibraryManagementSystem.enums.Role;
import com.LMS.LibraryManagementSystem.models.User;
import com.LMS.LibraryManagementSystem.services.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController{

    @Autowired
    private UserService userService;

    @GetMapping("/all-admins")
    public ResponseEntity<List<User>> getAdmins() {
        List<User> admin = userService.getAllUsersByRole(Role.ADMIN);
        return ResponseEntity.ok(admin);
    }
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> admin = userService.getAllUsersByRole(Role.USER);
        return ResponseEntity.ok(admin);
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAll() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
