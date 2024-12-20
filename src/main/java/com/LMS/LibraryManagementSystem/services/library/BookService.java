package com.LMS.LibraryManagementSystem.services.library;

import com.LMS.LibraryManagementSystem.enums.BookStatus;
import com.LMS.LibraryManagementSystem.models.Book;
import com.LMS.LibraryManagementSystem.models.BookCopy;
import com.LMS.LibraryManagementSystem.repository.BookCopyRepository;
import com.LMS.LibraryManagementSystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    public Book addNewBook(String name, String author, int volume, int publicationYear, String isbn, String location, String publicationName) {
        // Check if the book already exists
        Book existingBook = bookRepository.findByNameAndAuthorAndPublicationYearAndVolumeAndPublicationName(name, author, publicationYear, volume, publicationName)
                .orElse(null);

        if (existingBook == null) {
            // Create a new book entry
            Book newBook = new Book();
            newBook.setName(name);
            newBook.setAuthor(author);
            newBook.setVolume(volume);
            newBook.setPublicationName(publicationName);
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

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Fetch books by name
    public List<Book> getBooksByName(String name) {
        return bookRepository.findByName(name);
    }

    // Fetch books by publication name
    public List<Book> getBooksByPublicationName(String publicationName) {
        return bookRepository.findByPublicationName(publicationName);
    }

    // Fetch books by author
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
}

