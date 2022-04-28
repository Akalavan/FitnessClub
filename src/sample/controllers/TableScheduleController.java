package sample.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.controllers.edits.EditScheduleForm;
import sample.interfaces.impls.CollectionControlScheduleGroup;
import sample.interfaces.impls.CollectionControlTrainers;
import sample.objects.Person;
import sample.objects.schedule.ScheduleGroup;

import java.io.IOException;

public class TableScheduleController {

    private Parent fxmlSh;
    EditScheduleForm chg;
    Stage schg;
    private static CollectionControlScheduleGroup controlSchedule = new CollectionControlScheduleGroup();
    public TableView<ScheduleGroup> tableSchedule;
    public TableColumn<ScheduleGroup, String> columnSchedule;
    //   public TableColumn<ScheduleGroup, String> columnGroup;
    public TableColumn<ScheduleGroup, String> columnTrainer;
    //  public TableColumn<ScheduleGroup, String> columnClients;


    public void fillDate() {

        //   columnId.setCellValueFactory(new PropertyValueFactory<Trainer, String>("id_trainers"));
        columnSchedule.setCellValueFactory(new PropertyValueFactory<ScheduleGroup, String>("date_schedule"));
        //  columnGroup.setCellValueFactory(new PropertyValueFactory<ScheduleGroup, String>("id_group"));
        columnTrainer.setCellValueFactory(new PropertyValueFactory<ScheduleGroup, String>("FIOTrainer"));
        //  columnClients.setCellValueFactory(new PropertyValueFactory<ScheduleGroup, String>("count_clients"));
        tableSchedule.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                if (schg == null) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("../fxml/formScheduleGroup.fxml"));
                    try {
                        fxmlSh = fxmlLoader.load();
                        chg = fxmlLoader.getController();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    schg = reg(schg, fxmlSh, "Расписание группы");
                    System.out.println("ok");
                }
                chg.setSchedule(tableSchedule.getSelectionModel().getSelectedItem().getDate_schedule());
                chg.setScheduleGroup(tableSchedule.getSelectionModel().getSelectedItem());
                showDialog(schg);
            }
        });
        //  controlSchedule.date();
        // tableSchedule.setItems(controlSchedule.getScheduleList());
    }

    void setDate(int id) {
        if (!controlSchedule.getScheduleList().isEmpty()) controlSchedule.clear();
        controlSchedule.setId_group(id);
        controlSchedule.date();
        tableSchedule.setItems(controlSchedule.getScheduleList());
    }

    @FXML
    private void initialize() {
        fillDate();
    }

    public static CollectionControlScheduleGroup getControlSchedule() {
        return controlSchedule;
    }

    private void showDialog(Stage stage) {
        stage.showAndWait();
    }

    private Stage reg(Stage stage, Parent parent, String title) {
        if (stage == null) {
            stage = new Stage();
            stage.setTitle(title);
            stage.setResizable(false);
            stage.setScene(new Scene(parent));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(Controller.getSchg());
        }
        return stage;
    }
}
