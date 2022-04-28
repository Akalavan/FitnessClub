package sample.controllers.edits;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.connectSQL.SQL;
import sample.objects.schedule.ScheduleGroup;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class EditScheduleForm {
    @FXML
    public DatePicker date;
    public Button btnOk;

    int idSchedule;

    private String schedule;
    private ScheduleGroup scheduleGroup;

    @FXML
    void initialize() {
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        date.setConverter(converter);
        date.setPromptText("yyyy-MM-dd");
    }

    public void setSchedule(String schedule) {
        System.out.println(schedule);
        this.schedule = schedule;
        LocalDate todayLocalDate = LocalDate.parse(schedule.split(" ")[0]);
        date.setValue(todayLocalDate);
    }

    public void actionButton(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) return;

        Button btnClick = (Button) source;

        switch (btnClick.getId()) {
            case "btnOk":
                String upSchedule = "UPDATE `schedule_group` SET `date_schedule` = '" + date.getEditor().getText() + " " + schedule.split(" ")[1] + "' WHERE `schedule_group`.`id_schedule` = 4;";
                System.out.println(upSchedule);
                SQL.update(upSchedule, null);
                scheduleGroup.setDate_schedule(date.getEditor().getText() + " " + schedule.split(" ")[1]);
                actionClose(actionEvent);
        }
    }

    public ScheduleGroup getScheduleGroup() {
        return scheduleGroup;
    }

    public void setScheduleGroup(ScheduleGroup scheduleGroup) {
        this.scheduleGroup = scheduleGroup;
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }
}
