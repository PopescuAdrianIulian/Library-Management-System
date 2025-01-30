package com.library.Service;

import com.library.Entity.Book;
import com.library.Repository.BookRepository;
import com.library.Util.Availability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Transactional
    public Book addBook(Book book) {
        book.setAvailability(Availability.Available);
        bookRepository.save(book);
        return book;
    }

    public Optional<Book> getBookById(int id) {
        return bookRepository.findById(id);
    }

    public List<Book> bookList() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book updateBook(int id, Book updatedbook) {
        Book tempBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        tempBook.setTitle(updatedbook.getTitle());
        tempBook.setAuthor(updatedbook.getAuthor());
        tempBook.setAvailability(updatedbook.getAvailability());
        return bookRepository.save(tempBook);

    }

    @Transactional
    public void deleteBookById(int id) {
        bookRepository.deleteById(id);
    }
}
