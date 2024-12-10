package controller.libraryapp;

import Util.LoanDAO;
import Util.UserDAO;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Loan;
import model.User;

import java.time.LocalDate;

public class ManageUserController {

    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, Integer> userIdColumn;
    @FXML
    private TableColumn<User, String> userNameColumn;
    @FXML
    private TableColumn<User, String> userEmailColumn;
    @FXML
    private TableView<Loan> loanTableView;
    @FXML
    private TableColumn<Loan, String> bookIsbnColumn;
    @FXML
    private TableColumn<Loan, LocalDate> borrowDateColumn;
    @FXML
    private TableColumn<Loan, LocalDate> returnDateColumn;
    @FXML
    private TableColumn<Loan, Integer> quantityColumn;

    public void initialize() {
        userIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getId()));
        userNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUserName()));
        userEmailColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));

        bookIsbnColumn.setCellValueFactory(cellData->new SimpleObjectProperty<>(cellData.getValue().getBook().getIsbn()));
        borrowDateColumn.setCellValueFactory(cellData ->new SimpleObjectProperty<>(cellData.getValue().getLoanDate()));
        returnDateColumn.setCellValueFactory(cellData ->new SimpleObjectProperty<>(cellData.getValue().getReturnDate()));
        quantityColumn.setCellValueFactory(cellData ->new SimpleObjectProperty<>(cellData.getValue().getQuantity()));

        loanTableView.setVisible(false);
        userTableView.setItems(getUserList());
    }

    public ObservableList<User> getUserList() {
        return FXCollections.observableArrayList(UserDAO.getAllUsers());
    }

    public ObservableList<Loan> getLoanList(User user) {
        return FXCollections.observableList(LoanDAO.getLoanByUserId(user.getId()));
    }

    @FXML
    public void handleLoanDetails() {
        User selectedUser = userTableView.getSelectionModel().getSelectedItem();

        if(selectedUser != null) {
            ObservableList<Loan> loanList = getLoanList(selectedUser);
            loanTableView.setItems(loanList);
            loanTableView.setVisible(true);
            userTableView.setVisible(false);
        }
    }
}