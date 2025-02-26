package com.library.Service;

import com.library.Entity.Book;
import com.library.Entity.Loan;
import com.library.Repository.BookRepository;
import com.library.Repository.LoanRepository;
import com.library.Util.Availability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;


    @Transactional
    public Loan borrowBook(int id, String name) {
        Book tempBook = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        tempBook.setAvailability(Availability.UNAVAILABLE);

        Loan loan = new Loan();
        loan.setBook(tempBook);
        loan.setName(name);
        loan.setBorrowDate(LocalDate.now());
        loan.setReturnDate(LocalDate.now().plusDays(30));
        loan.setReturned(false);
        return loanRepository.save(loan);
    }

    @Transactional
    public Loan returnBook(int id) {
        Loan tempLoan = loanRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        Book tempBook = tempLoan.getBook();

        if (tempLoan.isReturned()) {
            throw new IllegalStateException("Loan already returned");
        }

        tempBook.setAvailability(Availability.AVAILABLE);
        tempLoan.setReturned(true);
        loanRepository.save(tempLoan);
        bookRepository.save(tempBook);
        return tempLoan;
    }

    public Loan getLoansById(int id) {
        return loanRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Loan not found"));
    }

    public List<Loan> getLoanLists() {
        return loanRepository.findAll();
    }

    public void deleteLoan(int id) {
        loanRepository.deleteById(id);
    }

    public List<Loan> getLoanList() {
        return loanRepository.findAll();
    }

}
