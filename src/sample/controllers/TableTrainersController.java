package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.interfaces.impls.CollectionControlTrainers;
import sample.interfaces.impls.CollectionOperatingHall;
import sample.objects.Trainer;

public class TableTrainersController {
    //  public TableColumn<Trainer, String> columnId;
    public TableColumn<Trainer, String> columnName;
    public TableColumn<Trainer, String> columnSurname;
    public TableColumn<Trainer, String> columnPatronymic;
    public TableColumn<Trainer, String> columnGroup;
    public TableColumn<Trainer, String> columnSalary;
    public TableView<Trainer> tableTrainers;

    private static CollectionControlTrainers controlTrainers = new CollectionControlTrainers();

    public void fillDate() {
        //   columnId.setCellValueFactory(new PropertyValueFactory<Trainer, String>("id_trainers"));
        columnName.setCellValueFactory(new PropertyValueFactory<Trainer, String>("name_trainer"));
        columnSurname.setCellValueFactory(new PropertyValueFactory<Trainer, String>("surname_trainer"));
        columnPatronymic.setCellValueFactory(new PropertyValueFactory<Trainer, String>("patronymic_trainer"));
        columnGroup.setCellValueFactory(new PropertyValueFactory<Trainer, String>("group"));
        columnSalary.setCellValueFactory(new PropertyValueFactory<Trainer, String>("salary_trainer"));
        controlTrainers.date();
        tableTrainers.setItems(CollectionControlTrainers.getTrainersList());
    }

    @FXML
    private void initialize() {
        fillDate();
    }

    public static CollectionControlTrainers getControlTrainers() {
        return controlTrainers;
    }
}
