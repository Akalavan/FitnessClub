package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.interfaces.impls.CollectionAllStack;
import sample.objects.Stock;

public class TableAllStockController {
    public TableColumn<Stock, String> columnId;
    public TableColumn<Stock, String> dateEnd;
    public TableColumn<Stock, String> dateStart;
    public TableColumn<Stock, String> condition;
    public TableColumn<Stock, String> comment;
    private static CollectionAllStack allStockBase = new CollectionAllStack();
    public TableView<Stock> tableAllStock;

    public void fillDate() {
        columnId.setCellValueFactory(new PropertyValueFactory<Stock, String>("id_stock"));
        dateStart.setCellValueFactory(new PropertyValueFactory<Stock, String>("dateStart"));
        dateEnd.setCellValueFactory(new PropertyValueFactory<Stock, String>("dateEnd"));
        condition.setCellValueFactory(new PropertyValueFactory<Stock, String>("condition"));
        comment.setCellValueFactory(new PropertyValueFactory<Stock, String>("comment"));
        allStockBase.date();
        tableAllStock.setItems(CollectionAllStack.getAllStockList());
    }

    @FXML
    private void initialize() {
        fillDate();
    }

    public static CollectionAllStack getAllStockBase() {
        return allStockBase;
    }
}
