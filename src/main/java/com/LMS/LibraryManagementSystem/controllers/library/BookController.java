package com.LMS.LibraryManagementSystem.controllers.library;

import com.LMS.LibraryManagementSystem.models.Book;
import com.LMS.LibraryManagementSystem.services.library.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestParam String name,
                                          @RequestParam String author,
                                          @RequestParam int volume,
                                          @RequestParam int publicationYear,
                                          @RequestParam String isbn,
                                          @RequestParam(required = false) String location,
                                          @RequestParam String publicationName) {

        if (bookService.checkisbn(isbn)) {
            return ResponseEntity.badRequest().body("Book with ISBN " + isbn + " already exists.");
        }
        Book addedBook = bookService.addNewBook(name, author, volume, publicationYear, isbn, location,publicationName);
        return ResponseEntity.ok("Book added successfully! Book ID: " + addedBook.getId());
    }
}

