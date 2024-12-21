package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.enums.Role;
import com.LMS.LibraryManagementSystem.models.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query(value = "SELECT email FROM users WHERE email = :email", nativeQuery = true)
    List<String> doesEmailExist(@Param("email")String email);

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User getUserByEmail(@Param("email")String email);

    // GET USER ID BY EMAIL:
    @Query(value = "SELECT user_id FROM users WHERE email = :email ", nativeQuery = true)
    int getUserIdByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users (first_name, last_name, email, password, role,phone_number) VALUES (:first_name, :last_name, :email, :password, :role, :phone_number)", nativeQuery = true)
    int signUpUser(@Param("first_name") String first_name,
                   @Param("last_name") String last_name,
                   @Param("email") String email,
                   @Param("password") String password,
                   @Param("role") String role,
                   @Param("phone_number") long phone_number);


    // CHECK IF EMAIL EXISTS:The query is derived from the method name (existsBy + Email).
    boolean existsByEmail(String email);

    //checks if the user exists by email and password
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email AND u.password = :password")
    boolean existsByEmailAndPassword(String email, String password);

    // Query to get password by email
    @Query(value = "SELECT password FROM users WHERE email = :email", nativeQuery = true)
    String getPasswordByEmail(@Param("email") String email);

    List<User> findByRole(Role role);

    @Query("SELECT u.email FROM User u WHERE u.user_id = :userId")
    Optional<String> findEmailByUserId(Long userId);
}

// END OF USER REPOSITORY INTERFACE.
