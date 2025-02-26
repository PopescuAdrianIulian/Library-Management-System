package com.library.Controller;

import com.library.Entity.Book;
import com.library.Entity.Loan;
import com.library.Service.BookService;
import com.library.Service.LoanService;
import com.library.Util.Availability;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final LoanService loanService;
    private final BookService bookService;

    @GetMapping("/list")
    public ResponseEntity<List<Book>> booksList() {
        logger.info("Fetching list of all books");
        List<Book> books = bookService.bookList();
        logger.debug("Retrieved {} books", books.size());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/loans/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable int loanId) {
        logger.info("Fetching loan with ID: {}", loanId);
        Loan userLoans = loanService.getLoansById(loanId);
        logger.debug("Retrieved loan: {}", userLoans);
        return ResponseEntity.ok(userLoans);
    }

    @GetMapping("/loans/list")
    public ResponseEntity<List<Loan>> getAllLoans() {
        logger.info("Fetching list of all loans");
        List<Loan> loanList = loanService.getLoanList();
        logger.debug("Retrieved {} loans", loanList.size());
        return ResponseEntity.ok(loanList);
    }

    @PostMapping("/borrow/{bookId}/{name}")
    public ResponseEntity<Loan> borrowBook(@Valid @PathVariable int bookId, @PathVariable String name) {
        logger.info("Borrowing book with ID: {} by user: {}", bookId, name);
        Book book = bookService.getBookById(bookId).orElseThrow(() -> {
            logger.error("Book not found with ID: {}", bookId);
            return new IllegalArgumentException("Book not found");
        });

        if (book.getAvailability() == Availability.UNAVAILABLE) {
            logger.warn("Book with ID: {} is unavailable", bookId);
            return ResponseEntity.badRequest().body(null);
        }

        Loan loan = loanService.borrowBook(bookId, name);
        logger.debug("Book borrowed successfully: {}", loan);
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/return/{loanId}")
    public ResponseEntity<Loan> returnBook(@PathVariable int loanId) {
        logger.info("Returning book for loan ID: {}", loanId);
        Loan tempLoan = loanService.returnBook(loanId);
        logger.debug("Book returned successfully: {}", tempLoan);
        return ResponseEntity.ok(tempLoan);
    }
}