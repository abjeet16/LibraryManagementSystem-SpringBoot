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
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private TaskScheduler taskScheduler; // To schedule tasks dynamically

    @Autowired
    private BookCopyService bookCopyService;

    private static final double DAILY_FINE = 10.0;

    @Transactional
    public IssuedBook issueBook(IssuedBookRequestDTO requestDTO) {
        // Find the book copy and check availability in a single step
        BookCopy bookCopy = bookCopyRepository.findById(requestDTO.getCopyId())
                .orElseThrow(() -> new IllegalArgumentException("Book copy not found with ID: " + requestDTO.getCopyId()));

        if (bookCopy.getStatus() == BookStatus.ISSUED || bookCopy.getStatus() == BookStatus.DAMAGED) {
            throw new IllegalStateException("Book copy is not available for issuing.");
        }

        // Update book status directly
        bookCopy.setStatus(BookStatus.ISSUED);

        // Create the issued book entity
        IssuedBook issuedBook = new IssuedBook();
        issuedBook.setUserId(requestDTO.getUserId());
        issuedBook.setBookCopy(bookCopy);
        issuedBook.setIssueDate(requestDTO.getIssueDate());
        issuedBook.setReturnDate(requestDTO.getReturnDate());
        issuedBook.setActualReturnDate(null);

        // Save both entities within a single transactional boundary
        bookCopyRepository.save(bookCopy);
        issuedBook = issuedBookRepository.save(issuedBook);

        // Retrieve email and send notification
        String email = userRepository.findEmailByUserId(requestDTO.getUserId());

        String emailSubject = "Book Borrowed: " + bookCopy.getBook().getName();
        String emailBody = "Dear User,\n\nYou have successfully borrowed the book titled '"
                + bookCopy.getBook().getName() + "'.\n"
                + "Please return it by " + issuedBook.getReturnDate() + " to avoid any fines.\n\n"
                + "Thank you.\nLibrary Management System.";

        // Async email notification
        emailService.sendEmailAsync(email, emailSubject,emailBody);

        // Schedule a reminder for the return date
        scheduleReturnReminder(email, bookCopy.getBook().getName(), issuedBook);

        return issuedBook;
    }

    private void scheduleReturnReminder(String email, String bookTitle, IssuedBook issuedBook) {
        // Convert LocalDate to Date for scheduling at 7:00 AM
        Date reminderDate = Date.from(issuedBook.getReturnDate()
                .atTime(2, 20) // Set to 7:00 AM
                .atZone(java.time.ZoneId.systemDefault()) // Handle time zone
                .toInstant());

        // Schedule task
        taskScheduler.schedule(() -> {
            // Fetch the latest state of the issued book
            Optional<IssuedBook> bookOptional = issuedBookRepository.findById(issuedBook.getIssueId());
            if (bookOptional.isPresent()) {
                IssuedBook book = bookOptional.get();
                // Check if the book has not been returned (actualReturnDate is null)
                if (book.getActualReturnDate() == null) {
                    // Send email reminder
                    String subject = "Book Return Reminder";
                    String body = "Dear User,\n\nThis is a reminder to return the book titled '"
                            + bookTitle + "' by today to avoid any fines.\n\n"
                            + "Thank you.\nLibrary Management System.";

                    try {
                        emailService.sendEmail(email, subject, body);
                    } catch (Exception e) {
                        // Log or handle the failure to send email
                        System.err.println("Failed to send reminder email: " + e.getMessage());
                    }
                }
            } else {
                // Handle case where the book is not found in the database
                System.err.println("Issued book with ID " + issuedBook.getIssueId() + " not found.");
            }
        }, reminderDate);  // Schedule the task to run at the reminderDate
    }

    public double returnBookWithFine(Long issueId) {
        Optional<IssuedBook> issuedBookOptional = issuedBookRepository.findById(issueId);
        if (issuedBookOptional.isPresent()) {
            IssuedBook issuedBook = issuedBookOptional.get();
            LocalDate today = LocalDate.now();
            issuedBook.setActualReturnDate(today); // Set the actual return date to today.

            double fine = 0;
            if (today.isAfter(issuedBook.getReturnDate())) {
                long overdueDays = java.time.temporal.ChronoUnit.DAYS.between(issuedBook.getReturnDate(), today);
                fine = overdueDays * DAILY_FINE;
            }

            issuedBookRepository.save(issuedBook); // Save the updated entity.
            bookCopyService.changeStatus(issuedBook.getBookCopy().getCopyId(), String.valueOf(BookStatus.AVAILABLE));
            return fine;
        }
        return -1; // Book not found with the given ID.
    }

    public List<IssuedBook> getAllIssuedBooks() {
        return issuedBookRepository.feachCurrentIssuedBooks();
    }

    public IssuedBook getIssuedBookById(Long issueId) {
        return issuedBookRepository.findById(issueId).orElse(null);
    }

    public List<IssuedBook> getMyIssues(int userId) {
        return issuedBookRepository.getMyIssues((long)userId);
    }
}




