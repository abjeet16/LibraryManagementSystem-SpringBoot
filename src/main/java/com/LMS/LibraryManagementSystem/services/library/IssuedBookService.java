package com.LMS.LibraryManagementSystem.services.library;

import com.LMS.LibraryManagementSystem.dto.requests.IssuedBookRequestDTO;
import com.LMS.LibraryManagementSystem.enums.BookStatus;
import com.LMS.LibraryManagementSystem.models.BookCopy;
import com.LMS.LibraryManagementSystem.models.IssuedBook;
import com.LMS.LibraryManagementSystem.repository.BookCopyRepository;
import com.LMS.LibraryManagementSystem.repository.IssuedBookRepository;
import com.LMS.LibraryManagementSystem.repository.UserRepository;
import com.LMS.LibraryManagementSystem.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class IssuedBookService {
    @Autowired
    private IssuedBookRepository issuedBookRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public IssuedBook issueBook(IssuedBookRequestDTO requestDTO) {
        // Find the book copy
        BookCopy bookCopy = bookCopyRepository.findById(requestDTO.getCopyId())
                .orElseThrow(() -> new IllegalArgumentException("Book copy not found with ID: " + requestDTO.getCopyId()));

        // Check if the book copy is available
        if (bookCopy.getStatus() == BookStatus.ISSUED || bookCopy.getStatus() == BookStatus.DAMAGED) {
            throw new IllegalStateException("Book copy is not available for issuing.");
        }

        // Mark the book copy as unavailable
        bookCopy.setStatus(BookStatus.ISSUED);
        bookCopyRepository.save(bookCopy);

        // Create and save the issued book
        IssuedBook issuedBook = new IssuedBook();
        issuedBook.setUserId(requestDTO.getUserId());
        issuedBook.setBookCopy(bookCopy);
        issuedBook.setIssueDate(requestDTO.getIssueDate());
        issuedBook.setReturnDate(requestDTO.getReturnDate());

        issuedBook = issuedBookRepository.save(issuedBook);

        String email = String.valueOf(userRepository.findEmailByUserId(requestDTO.getUserId()));

        // Send email to user upon issuing the book
        String issueEmailSubject = "Book Borrowed: " + bookCopy.getBook().getName();
        String issueEmailBody = "Dear User,\n\nYou have successfully borrowed the book titled '"
                + bookCopy.getBook().getName() + "'.\n"
                + "Please return it by " + issuedBook.getReturnDate() + " to avoid any fines.\n\n"
                + "Thank you.\nLibrary Management System.";
        emailService.sendEmail("avjeetyadav00@gmail.com", issueEmailSubject, issueEmailBody);

        return issuedBook;
    }
}


