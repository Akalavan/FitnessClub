package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.interfaces.impls.CollectionControlGroup;
import sample.objects.Group;


public class TableGroupController {

    private static CollectionControlGroup controlGroup = new CollectionControlGroup();
    @FXML
    private TableView<Group> tableViewGroup;
    public static TableView<Group> tableGroup;
    public TableColumn<Group, String> columnName;
    public TableColumn<Group, String> columnGroup;
    public TableColumn<Group, String> columnTrainer;
    public TableColumn<Group, String> columnClients;

    public static void setTableGroup(TableView<Group> tableViewGroup) {
        TableGroupController.tableGroup = tableViewGroup;
    }

    public static Group getSelectedItem() {
        return tableGroup.getSelectionModel().getSelectedItem();
    }

    public void fillDate() {
        //   columnId.setCellValueFactory(new PropertyValueFactory<Trainer, String>("id_trainers"));
        columnName.setCellValueFactory(new PropertyValueFactory<Group, String>("name"));
        columnGroup.setCellValueFactory(new PropertyValueFactory<Group, String>("id"));
        columnTrainer.setCellValueFactory(new PropertyValueFactory<Group, String>("FIOTrainer"));
        columnClients.setCellValueFactory(new PropertyValueFactory<Group, String>("count_clients"));
        controlGroup.date();
        tableViewGroup.setItems(CollectionControlGroup.getGroupsList());
    }

    @FXML
    private void initialize() {
        tableGroup = tableViewGroup;
        fillDate();
    }

    public static TableView<Group> getTableGroup() {
        return tableGroup;
    }

    public static CollectionControlGroup getControlGroup() {
        return controlGroup;
    }
}
