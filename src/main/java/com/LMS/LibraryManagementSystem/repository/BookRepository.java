package com.LMS.LibraryManagementSystem.repository;

import com.LMS.LibraryManagementSystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByNameAndAuthorAndPublicationYearAndVolumeAndPublicationName(
            String name, String author, int publicationYear, int volume, String publicationName);

    // Find books by name
    List<Book> findByName(String name);

    // Find books by publication name
    List<Book> findByPublicationName(String publicationName);

    // Find books by author
    List<Book> findByAuthor(String author);
}
