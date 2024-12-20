package com.LMS.LibraryManagementSystem.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "issued_book")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long issueId;

    @Column(nullable = false)
    private Long userId; // ID of the user who borrowed the book.

    @ManyToOne
    @JoinColumn(name = "copy_id", nullable = false)
    private BookCopy bookCopy;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private LocalDate returnDate;

    @Column
    private LocalDate actualReturnDate;
}

