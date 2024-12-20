package com.LMS.LibraryManagementSystem.dto.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class IssuedBookRequestDTO {
    private Long userId; // ID of the user borrowing the book.
    private Long copyId; // ID of the book copy being borrowed.
    private LocalDate issueDate; // Date when the book is issued.
    private LocalDate returnDate; // Expected return date.
}

