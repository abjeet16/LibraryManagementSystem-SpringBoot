package com.LMS.LibraryManagementSystem.services.library;

import com.LMS.LibraryManagementSystem.models.Book;
import com.LMS.LibraryManagementSystem.models.BookCopy;
import com.LMS.LibraryManagementSystem.repository.BookCopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    public List<BookCopy> findCopiesByBookId(Long bookId) {
        return bookCopyRepository.findByBookId(bookId);
    }

    public BookCopy findByIsbn(String isbn) {
        return bookCopyRepository.findByisbn(isbn);
    }

    public boolean existByCopyId(Long copyId) {
        return bookCopyRepository.existsByCopyId(copyId);
    }
}
