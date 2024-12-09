package model;


import java.time.LocalDate;

public class Loan {
    private String loanId;
    private User user;
    private Book book;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private String status;
    private int quantity;

    public Loan(User user, Book book, LocalDate loanDate, int quantity) {
        this.user = user;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = loanDate.plusDays(2);
        this.quantity = quantity;
    }

    public Loan(Book book, LocalDate loanDate, LocalDate returnDate, int quantity) {
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.quantity = quantity;
    }
    public Loan(User user, Book book) {
        this.user = user;
        this.book = book;
        this.loanDate = null;
        status = "Returned";
        this.returnDate = null;
        this.quantity = 0;
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

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }

    public String getStatus() {
        return status;
    }

    public int getQuantity() {
        return quantity;
    }
}
