package Util;

import model.Book;
import model.Loan;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    private static Connection conn = null;


    public static void addLoan(Loan loan) {
        String sql = "insert into loan(userId, bookISBN, borrowDate, returnDate,status, quantity) values(?,?,?,?,?,?)";
        try {
            conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, loan.getUser().getId());
            ps.setString(2, loan.getBook().getIsbn());
            ps.setDate(3, Date.valueOf(loan.getLoanDate()));
            ps.setDate(4, Date.valueOf(loan.getReturnDate()));
            ps.setString(5, loan.getStatus());
            ps.setInt(6, loan.getQuantity());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Loan> getLoanByUserId(int id) {
        List<Loan> loans = new ArrayList<>();
        String sql = "select bookISBN from loan where userId = ?";
        try {
            conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = BookDAO.getBookByISBN(rs.getString("bookISBN"));
                Loan loan = new Loan(book);
                loans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

}
