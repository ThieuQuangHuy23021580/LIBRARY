package model;

import java.time.LocalDate;

public class Loan {
    private String loanId;
    private User user;
    private Book book;
    private LocalDate loanDate;
    private LocalDate returnDate;

    public Loan(String loanId, User user, Book book, LocalDate loanDate) {
        this.loanId = loanId;
        this.user = user;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = null;
    }

    public String getLoanId() {
        return loanId;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
