package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.models.IssuedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {
    List<IssuedBook> findByReturnDate(LocalDate returnDate);

    @Query("SELECT ib FROM IssuedBook ib WHERE ib.actualReturnDate IS NULL")
    List<IssuedBook> feachCurrentIssuedBooks();
}
