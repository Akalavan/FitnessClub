package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.interfaces.impls.CollectionPersonBase;
import sample.objects.Person;

public class FirstTabController {
    public TableColumn<Person, String> columnNumber;
    public TableColumn<Person, String> columnType;
    public TableColumn<Person, String> columnName;
    public TableColumn<Person, String> columnSurname;
    public TableColumn<Person, String> columnPatronymic;
    public TableColumn<Person, String> columnGroup;
    public TableColumn<Person, String> columnSchedulePrivate;
    private static CollectionPersonBase personBaseFirstTable = new CollectionPersonBase();
    @FXML
    private TableView<Person> tableViewFirst;
    private static TableView<Person> tableView;

    public static void setTableViewFirst(TableView<Person> tableViewFirst) {
        FirstTabController.tableView = tableViewFirst;
    }

    public void fillDate() {
        columnNumber.setCellValueFactory(new PropertyValueFactory<Person, String>("tik_id"));
        columnType.setCellValueFactory(new PropertyValueFactory<Person, String>("tik_type"));
        columnName.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<Person, String>("surname"));
        columnPatronymic.setCellValueFactory(new PropertyValueFactory<Person, String>("patronymic"));
        columnGroup.setCellValueFactory(new PropertyValueFactory<Person, String>("group"));
        columnSchedulePrivate.setCellValueFactory(new PropertyValueFactory<Person, String>("schedulePrivate"));
        System.out.println(personBaseFirstTable.getPersonList().size());
        personBaseFirstTable.date();
        tableViewFirst.setItems(personBaseFirstTable.getPersonList());
        System.out.println(personBaseFirstTable.getPersonList().size());
    }

    @FXML
    private void initialize() {
        setTableViewFirst(tableViewFirst);
        fillDate();
    }

    static CollectionPersonBase getPersonBase() {
        return personBaseFirstTable;
    }

    public static Person getSelectedItem() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    public static TableView<Person> getTableView() {
        return tableView;
    }

    public TableColumn<Person, String> getColumnType() {
        return columnType;
    }
}
