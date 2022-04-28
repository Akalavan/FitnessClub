package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.interfaces.impls.CollectionOperatingHall;
import sample.objects.Hall;
import sample.objects.MoneyProfit;

import java.io.IOException;

public class ThirdTabController {
    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Parent fxmlEdit;
    public TableColumn<MoneyProfit, String> columnIdHall;
    public TableColumn<MoneyProfit, String> columnTransactions;
    public TableColumn<MoneyProfit, String> columnDate;
    public TableColumn<MoneyProfit, String> columnMoney;
    public TableView<MoneyProfit> tableViewThird;

    private static CollectionOperatingHall moneyBaseThirdTable = new CollectionOperatingHall();

    public void fillDate() {
        columnIdHall.setCellValueFactory(new PropertyValueFactory<MoneyProfit, String>("id_hall"));
        columnTransactions.setCellValueFactory(new PropertyValueFactory<MoneyProfit, String>("id_transactions"));
        columnDate.setCellValueFactory(new PropertyValueFactory<MoneyProfit, String>("date_transactions"));
        columnMoney.setCellValueFactory(new PropertyValueFactory<MoneyProfit, String>("money_as_much"));
        moneyBaseThirdTable.getDateMoneyProfit();
        tableViewThird.setItems(moneyBaseThirdTable.getMoneyProfits());

    }

    @FXML
    private void initialize() {
//        try {
//            fxmlLoader.setLocation(getClass().getResource("../fxml/tab3.fxml"));
//            fxmlEdit = fxmlLoader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        fillDate();
    }

    public Parent getFxmlEdit() {
        return fxmlEdit;
    }

    public static CollectionOperatingHall getHallBaseSecondTable() {
        return moneyBaseThirdTable;
    }
}
