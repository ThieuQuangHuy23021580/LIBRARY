package Util;

import javafx.scene.chart.PieChart;
import model.Book;
import model.Loan;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {
    private static Connection conn = null;


    public static void addLoan(Loan loan) {
        String sql = "insert into loan(userId, bookISBN, borrowDate, returnDate, quantity) values(?,?,?,?,?)";
        try {
            conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, loan.getUser().getId());
            ps.setString(2, loan.getBook().getIsbn());
            ps.setDate(3, Date.valueOf(loan.getLoanDate()));
            ps.setDate(4, Date.valueOf(loan.getReturnDate()));
            ps.setInt(5, loan.getQuantity());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getBorrowQuantity(int userId, String isbn) {
         String sql = "select quantity from loan where userId = ? and bookISBN = ?";
         try {
             conn = DatabaseConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             stmt.setInt(1, userId);
             stmt.setString(2, isbn);
             ResultSet rs = stmt.executeQuery();
             if (rs.next()) {
                 return rs.getInt(1);
             }
         }
         catch(SQLException e) {
             e.printStackTrace();
         }
         return 0;
    }

    public static void updateLoan(int userId, String isbn, int returnQuantity) {
        String sql = "update loan set quantity = quantity - ? where userId = ? and bookISBN = ?";
        try {
            conn = DatabaseConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, isbn);
            stmt.setInt(3, returnQuantity );
            stmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLoan(int userId, String isbn) {
        String sql = "delete from loan where userId = ? and bookISBN = ?";
        try {
            conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, isbn);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Loan> getLoanByUserId(int id) {
        List<Loan> loans = new ArrayList<>();
        String sql = "select bookISBN,borrowDate, returnDate,quantity from loan where userId = ?";
        try {
            conn = DatabaseConnect.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = BookDAO.getBookByISBN(rs.getString("bookISBN"));
                Loan loan = new Loan(book, rs.getDate("borrowDate").toLocalDate(), rs.getDate("returnDate").toLocalDate(), rs.getInt("quantity"));
                loans.add(loan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loans;
    }

}
