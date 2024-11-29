package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoanManager {
    private List<Loan> loans = new ArrayList<>();

    public void addLoan(Loan loan) {
        loans.add(loan);
    }

    public List<Loan> getLoansByUser(User user) {
        List<Loan> loansByUser = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getUser().equals(user)) {
                loansByUser.add(loan);
            }
        }
        return loansByUser;
    }

    public List<Loan> getActiveLoansByUser(User user) {
        return loans.stream()
                .filter(loan -> loan.getUser().equals(user) && loan.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    public List<Loan> getLoansByBook(Book book) {
        List<Loan> loansByBook = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getBook().equals(book)) {
                loansByBook.add(loan);
            }
        }
        return loansByBook;
    }
}
