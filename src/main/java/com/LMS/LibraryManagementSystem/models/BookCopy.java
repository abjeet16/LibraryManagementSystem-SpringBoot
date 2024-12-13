package com.LMS.LibraryManagementSystem.models;

import com.LMS.LibraryManagementSystem.enums.BookStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_copy")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long copyId;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private BookStatus status; // Values can be "available", "issued", or "damaged".

    @Column(nullable = true)
    private String location; // Optional: Shelf/Section location.
}

