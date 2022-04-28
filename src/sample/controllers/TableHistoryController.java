package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.interfaces.impls.CollectionControlHistory;
import sample.objects.Group;
import sample.objects.history;

import java.util.HashMap;

public class TableHistoryController {

    private static CollectionControlHistory controlHistory = new CollectionControlHistory();
    @FXML
    private TableView<history> tableHistory;
    public static TableView<Group> tableGroup;
    public TableColumn<history, String> columnType;
    public TableColumn<history, String> columnDate;
    public TableColumn<history, String> columnCost;


    public void fillDate() {
        //   columnId.setCellValueFactory(new PropertyValueFactory<Trainer, String>("id_trainers"));
        columnType.setCellValueFactory(new PropertyValueFactory<history, String>("type"));
        columnDate.setCellValueFactory(new PropertyValueFactory<history, String>("date"));
        columnCost.setCellValueFactory(new PropertyValueFactory<history, String>("cost"));
    }

    void setDate(int id) {
        if (!CollectionControlHistory.getHistoryList().isEmpty()) controlHistory.clear();
        controlHistory.setId_user(id);
        controlHistory.date();
        tableHistory.setItems(CollectionControlHistory.getHistoryList());
    }

    @FXML
    private void initialize() {
        fillDate();
    }

    public static TableView<Group> getTableGroup() {
        return tableGroup;
    }

    public static CollectionControlHistory getControlGroup() {
        return controlHistory;
    }
}
