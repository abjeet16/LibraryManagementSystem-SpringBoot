package com.LMS.LibraryManagementSystem.controllers.library;

import com.LMS.LibraryManagementSystem.dto.requests.IssuedBookRequestDTO;
import com.LMS.LibraryManagementSystem.models.Book;
import com.LMS.LibraryManagementSystem.models.BookCopy;
import com.LMS.LibraryManagementSystem.models.IssuedBook;
import com.LMS.LibraryManagementSystem.services.library.BookCopyService;
import com.LMS.LibraryManagementSystem.services.library.BookService;
import com.LMS.LibraryManagementSystem.services.library.IssuedBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookCopyService bookCopyService;

    @Autowired
    private IssuedBookService issuedBookService;

    //directly use common rest point for this
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

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
    //by-author by-publication by-name
    /*
    // View books by name
    @GetMapping("/by-name")
    public ResponseEntity<List<Book>> getBooksByName(@RequestParam String name) {
        List<Book> books = bookService.getBooksByName(name);
        return ResponseEntity.ok(books);
    }

    // View books by publication name
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
    }*/

    @GetMapping("/{bookId}/copies")
    public ResponseEntity<List<BookCopy>> getCopiesByBookId(@PathVariable Long bookId) {
        List<BookCopy> copies = bookCopyService.findCopiesByBookId(bookId);
        return ResponseEntity.ok(copies);
    }

    @GetMapping("/Isbn/{isbn}")
    public ResponseEntity<BookCopy> getBookByIsbn(@PathVariable String isbn) {
        BookCopy bookCopy = bookCopyService.findByIsbn(isbn);
        if (bookCopy == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookCopy);
    }

    @PostMapping("/issue")
    public ResponseEntity<?> issueBook(@RequestBody IssuedBookRequestDTO requestDTO) {
        if (!bookCopyService.existByCopyId(requestDTO.getCopyId())) {
            return ResponseEntity.badRequest().body("Book copy not found with ID: " + requestDTO.getCopyId());
        }
        IssuedBook issuedBook = issuedBookService.issueBook(requestDTO);
        return ResponseEntity.ok(issuedBook);
    }

    @PutMapping("/{issueId}/return")
    public ResponseEntity<Double> returnBook(@PathVariable Long issueId) {
        double fine = issuedBookService.returnBookWithFine(issueId);
        if (fine >= 0) {
            return ResponseEntity.ok(fine);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fine);
        }
    }
    @PutMapping("/{issueId}/change-status/{status}")
    public ResponseEntity<String> changeStatus(@PathVariable Long issueId, @PathVariable String status) {
        bookCopyService.changeStatus(issueId, status);
        return ResponseEntity.ok("Status changed successfully!");
    }

    @GetMapping("/issues")
    public ResponseEntity<List<IssuedBook>> getAllIssues() {
        List<IssuedBook> issuedBooks = issuedBookService.getAllIssuedBooks();
        return ResponseEntity.ok(issuedBooks);
    }

    @GetMapping("/issues/{issueId}")
    public ResponseEntity<IssuedBook> getIssueById(@PathVariable Long issueId) {
        IssuedBook issuedBook = issuedBookService.getIssuedBookById(issueId);
        if (issuedBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(issuedBook);
    }
}

