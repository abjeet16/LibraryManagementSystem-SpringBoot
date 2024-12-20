package com.LMS.LibraryManagementSystem.controllers.AUTH;


import com.LMS.LibraryManagementSystem.enums.Role;
import com.LMS.LibraryManagementSystem.services.JwtTokenService;
import com.LMS.LibraryManagementSystem.services.auth.MyCustomUserDetailService;
import com.LMS.LibraryManagementSystem.services.auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/auth")
public class adminAuthController {

    @Autowired
    private PasswordEncoder passwordEncoder; // Used to hash and compare passwords securely.

    @Autowired
    private AuthenticationManager authenticationManager; // Manages user authentication.

    @Autowired
    private UserService userService; // Handles user-related operations such as registration.

    @Autowired
    private MyCustomUserDetailService myCustomUserDetailService; // Custom service to load user details.

    @Autowired
    private JwtTokenService jwtTokenService; // Service for generating and managing JWT tokens.

    @PostMapping("/register")
    public ResponseEntity adminSignUp(@RequestParam("first_name") String firstName,
                                      @RequestParam("last_name") String lastName,
                                      @RequestParam("email") String email,
                                      @RequestParam("phone_number") long phoneNumber,
                                      @RequestParam("password") String password) {

        // This step should verify if the email is already registered. Implementation of this logic is not shown here.
        if (userService.doesWithEmailExist(email)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Email already exists");
        }

        // HASH PASSWORD:
        String hashed_password = passwordEncoder.encode(password);
        // Hashes the user's password using a secure algorithm (e.g., bcrypt) before storing it in the database.
        System.out.println(firstName + " " + lastName + " " + email + " " + hashed_password);

        // STORE USER://
        int result = userService.signUpUser(firstName, lastName, email, hashed_password, Role.ADMIN.toString(),phoneNumber);
        // Calls the `userService` to save the user's details in the database.
        // Returns `1` if successful or some other value if there's an issue.

        // CHECK FOR RESULT SET:
        if (result != 1) {
            return new ResponseEntity("Something went wrong", HttpStatus.BAD_REQUEST);
            // Returns an error response if the user could not be registered.
        }
        // END OF CHECK FOR RESULT SET.

        // RETURN SUCCESS RESPONSE:
        return new ResponseEntity("User Sign Up Successful!", HttpStatus.CREATED);
        // Returns a success message with HTTP status 201 (Created) if the registration is successful.

    }
    // END OF ADMIN SIGN UP POST METHOD
}
