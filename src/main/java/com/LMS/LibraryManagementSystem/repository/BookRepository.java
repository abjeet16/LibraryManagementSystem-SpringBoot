package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByNameAndPublicationYearAndVolume(String name, int publicationYear, int volume);
}
