package sample.controllers.edits;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.interfaces.impls.CollectionControlTrainers;
import sample.objects.Trainer;

import java.time.LocalDate;
import java.time.ZoneId;

public class EditAddingTrainerForm extends Edit {
    @FXML
    public TextField textName;
    @FXML
    public TextField textSurname;
    @FXML
    public TextField textPatronymic;
    @FXML
    public TextField textSalary;
    @FXML
    public ChoiceBox choiceGroup;
    @FXML
    public Button btnOk;
    @FXML
    public DatePicker dateHired;

    private Trainer trainer;

    private boolean b = false;

    @FXML
    void initialize() {
        ObservableList<String> tik = FXCollections.observableArrayList(CollectionControlTrainers.getGroup());
        choiceGroup.setItems(tik);
//        System.out.println(dateHired.getEditor().getText());
//        LocalDate todayLocalDate = LocalDate.;
//        java.sql.Date sqlDate = java.sql.Date.valueOf(todayLocalDate);
    }

    public TextField getTextName() {
        return textName;
    }

    public TextField getTextSurname() {
        return textSurname;
    }

    public TextField getTextPatronymic() {
        return textPatronymic;
    }

    public TextField getTextSalary() {
        return textSalary;
    }

    public ChoiceBox getChoiceGroup() {
        return choiceGroup;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }

//    public void actionButton(ActionEvent actionEvent) {
//
//
//    }

    public void actionButton(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) return;

        Button btn = (Button) source;
        System.out.println(dateHired.getEditor().getText());
        LocalDate todayLocalDate = dateHired.getValue();
        java.sql.Date sqlDate = java.sql.Date.valueOf(todayLocalDate);
        if (choiceGroup.getSelectionModel().getSelectedItem() == null) return;
        else {
            trainer.setGroup((String) choiceGroup.getSelectionModel().getSelectedItem());
            trainer.setName_trainer(textName.getText());
            trainer.setSurname_trainer(textSurname.getText());
            trainer.setPatronymic_trainer(textPatronymic.getText());
            trainer.setSalary_trainer(Integer.parseInt(textSalary.getText()));
            trainer.setDate_hired(dateHired.getEditor().getText());
            b = true;
            actionClose(actionEvent);
        }
    }

}

