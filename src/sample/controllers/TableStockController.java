package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.interfaces.impls.CollectionControlStock;
import sample.objects.Person;
import sample.objects.StockPerson;

import java.util.Date;

public class TableStockController {
    //    public TableColumn<StockPerson, String> columnNumber;
//    public TableColumn<StockPerson, String> stock;
//    public TableColumn<StockPerson, String> columnId;
//    public TableColumn<StockPerson, String> columnName;
//    public TableColumn<StockPerson, String> columnSurname;
//    public TableColumn<StockPerson, String> columnPatronymic;
    private static CollectionControlStock stockBase = new CollectionControlStock();
//    public TableColumn<StockPerson, String> columnEmail;
//    public TableColumn<StockPerson, String> columnStatus;

    public TableColumn<Person, String> stock;
    public TableColumn<Person, String> columnId;
    public TableColumn<Person, String> columnName;
    public TableColumn<Person, String> columnSurname;
    public TableColumn<Person, String> columnPatronymic;
    private static CollectionControlStock stocks = new CollectionControlStock();
    public TableColumn<Person, String> columnEmail;
    public TableColumn<Person, String> columnStatus;

    @FXML
    private TableView<Person> tableStock;
    // private TableView<StockPerson> tableStock;

    public void fillDate() {
//        columnId.setCellValueFactory(new PropertyValueFactory<StockPerson, String>("tik_id"));
//        stock.setCellValueFactory(new PropertyValueFactory<StockPerson, String>("stock"));
//        columnName.setCellValueFactory(new PropertyValueFactory<StockPerson, String>("name"));
//        columnSurname.setCellValueFactory(new PropertyValueFactory<StockPerson, String>("surname"));
//        columnPatronymic.setCellValueFactory(new PropertyValueFactory<StockPerson, String>("patronymic"));
//        columnNumber.setCellValueFactory(new PropertyValueFactory<StockPerson, String>("number"));
//        columnEmail.setCellValueFactory(new PropertyValueFactory<StockPerson, String>("email"));
//        columnStatus.setCellValueFactory(new PropertyValueFactory<StockPerson, String>("status"));
//        tableStock.setItems(CollectionControlStock.getStockList());
        columnId.setCellValueFactory(new PropertyValueFactory<Person, String>("tik_id"));
        stock.setCellValueFactory(new PropertyValueFactory<Person, String>("stocks"));
        columnName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
        columnPatronymic.setCellValueFactory(new PropertyValueFactory<Person, String>("patronymic"));
        columnEmail.setCellValueFactory(new PropertyValueFactory<Person, String>("mail"));
        //  columnStatus.setCellValueFactory(new PropertyValueFactory<Person, String>("status"));
        CollectionControlStock.getStockList();
        tableStock.setItems(CollectionControlStock.getStockObservableList());

    }

    @FXML
    private void initialize() {
        fillDate();
    }

    public static void goDate() {
        stockBase.date();
    }

    public static CollectionControlStock getStockBase() {
        return stockBase;
    }

}



