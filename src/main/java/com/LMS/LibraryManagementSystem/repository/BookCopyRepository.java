package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.enums.BookStatus;
import com.LMS.LibraryManagementSystem.models.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {


    Optional<BookCopy> findByBook_IdAndStatus(Long bookId, BookStatus status);
    boolean existsByIsbn(String isbn);

    List<BookCopy> findByBookId(Long bookId);

    List<BookCopy> findByStatus(BookStatus status);

    BookCopy findByisbn(String isbn);

    boolean existsByCopyId(Long copyId);
}

