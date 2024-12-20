package com.LMS.LibraryManagementSystem.controllers.library.CommonRestPoints;

import com.LMS.LibraryManagementSystem.models.Book;
import com.LMS.LibraryManagementSystem.models.BookCopy;
import com.LMS.LibraryManagementSystem.services.library.BookCopyService;
import com.LMS.LibraryManagementSystem.services.library.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("books")
public class CommonBookCopyController {

    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<Book>> getBooksByName(@RequestParam String name) {
        List<Book> books = bookService.getBooksByName(name);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/by-publication")
    public ResponseEntity<List<Book>> getBooksByPublicationName(@RequestParam String publicationName) {
        List<Book> books = bookService.getBooksByPublicationName(publicationName);
        return ResponseEntity.ok(books);
    }

    // View books by author
    @GetMapping("/by-author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String author) {
        List<Book> books = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(books);
    }
}
