package com.library.Controller;

import com.library.Entity.Book;
import com.library.Service.BookService;
import com.library.Service.LoanService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final LoanService loanService;
    private final BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<Book> addBooks(@RequestBody Book book) {
        logger.info("Adding a new book: {}", book.getTitle());
        Book savedBook = bookService.addBook(book);
        logger.debug("Book added successfully: {}", savedBook);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        logger.info("Updating book with ID: {}", id);
        Book updatedBook = bookService.updateBook(id, book);
        logger.debug("Book updated successfully: {}", updatedBook);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        logger.warn("Deleting book with ID: {}", id);
        bookService.deleteBookById(id);
        logger.info("Book deleted successfully: {}", id);
        return ResponseEntity.ok("Book deleted successfully");
    }

    @DeleteMapping("loan/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable int id) {
        logger.warn("Deleting loan with ID: {}", id);
        loanService.deleteLoan(id);
        logger.info("Loan deleted successfully: {}", id);
        return ResponseEntity.ok("Deleted successfully!");
    }
}