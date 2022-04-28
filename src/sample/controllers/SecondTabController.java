package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.interfaces.impls.CollectionOperatingHall;
import sample.interfaces.impls.CollectionPersonBase;
import sample.objects.Hall;
import sample.start.Main;

public class SecondTabController {
    public TableColumn<Hall, String> columnIdHall;
    public TableColumn<Hall, String> columnClient;
    public TableColumn<Hall, String> columnInventory;
    public TableColumn<Hall, String> columnMoney;
    public TableView<Hall> tableViewSecond;

    private static CollectionOperatingHall hallBaseSecondTable = new CollectionOperatingHall();

    public void fillDate() {
        columnIdHall.setCellValueFactory(new PropertyValueFactory<Hall, String>("id_hall"));
        columnClient.setCellValueFactory(new PropertyValueFactory<Hall, String>("count_person"));
        columnInventory.setCellValueFactory(new PropertyValueFactory<Hall, String>("count_inventory"));
        columnMoney.setCellValueFactory(new PropertyValueFactory<Hall, String>("money"));
        hallBaseSecondTable.getDateHall();
        tableViewSecond.setItems(hallBaseSecondTable.getHallList());
    }

    @FXML
    private void initialize() {
        fillDate();
    }

    public static CollectionOperatingHall getHallBaseSecondTable() {
        return hallBaseSecondTable;
    }
}
