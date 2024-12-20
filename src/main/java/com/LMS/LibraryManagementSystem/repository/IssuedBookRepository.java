package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.models.IssuedBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {
}
