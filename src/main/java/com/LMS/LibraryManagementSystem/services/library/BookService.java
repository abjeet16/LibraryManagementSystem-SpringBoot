package com.LMS.LibraryManagementSystem.services.library;

import com.LMS.LibraryManagementSystem.enums.BookStatus;
import com.LMS.LibraryManagementSystem.models.Book;
import com.LMS.LibraryManagementSystem.models.BookCopy;
import com.LMS.LibraryManagementSystem.repository.BookCopyRepository;
import com.LMS.LibraryManagementSystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    public Book addNewBook(String name, String author, int volume, int publicationYear, String isbn, String location) {
        // Check if the book already exists
        Book existingBook = bookRepository.findByNameAndPublicationYearAndVolume(name, publicationYear, volume)
                .orElse(null);

        if (existingBook == null) {
            // Create a new book entry
            Book newBook = new Book();
            newBook.setName(name);
            newBook.setAuthor(author);
            newBook.setVolume(volume);
            newBook.setPublicationYear(publicationYear);
            Book savedBook = bookRepository.save(newBook);

            // Create a book copy for the new book
            BookCopy newBookCopy = new BookCopy();
            newBookCopy.setBook(savedBook);
            newBookCopy.setIsbn(isbn);
            newBookCopy.setLocation(location);
            newBookCopy.setStatus(BookStatus.AVAILABLE);
            bookCopyRepository.save(newBookCopy);

            return savedBook;
        } else {
            // Add a new copy for the existing book
            BookCopy newBookCopy = new BookCopy();
            newBookCopy.setBook(existingBook);
            newBookCopy.setIsbn(isbn);
            newBookCopy.setLocation(location);
            newBookCopy.setStatus(BookStatus.AVAILABLE);
            bookCopyRepository.save(newBookCopy);

            return existingBook;
        }
    }

    public boolean checkisbn(String isbn) {
        return bookCopyRepository.existsByIsbn(isbn);
    }
}

