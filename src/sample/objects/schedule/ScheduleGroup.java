package sample.objects.schedule;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import sample.objects.Trainer;

public class ScheduleGroup {

    private Trainer trainer;

    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty date_schedule = new SimpleStringProperty("");
    private SimpleStringProperty FIOTrainer = new SimpleStringProperty("");
    private SimpleIntegerProperty id_group = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty count_clients = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty day = new SimpleIntegerProperty(0);


    public ScheduleGroup(int id, String date_schedule, Trainer trainer, int id_group, int count_clients, int day) {
        //  this.id_trainers = new SimpleIntegerProperty(id_trainers);
        this.id = new SimpleIntegerProperty(id);
        this.date_schedule = new SimpleStringProperty(date_schedule);
        this.trainer = trainer;
        setFIOTrainer();
        this.id_group = new SimpleIntegerProperty(id_group);
        this.count_clients = new SimpleIntegerProperty(count_clients);
        this.day = new SimpleIntegerProperty(day);
    }


    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDate_schedule() {
        return date_schedule.get();
    }

    public SimpleStringProperty date_scheduleProperty() {
        return date_schedule;
    }

    public void setDate_schedule(String date_schedule) {
        this.date_schedule.set(date_schedule);
    }

    public int getId_group() {
        return id_group.get();
    }

    public SimpleIntegerProperty id_groupProperty() {
        return id_group;
    }

    public void setId_group(int id_group) {
        this.id_group.set(id_group);
    }

    public int getCount_clients() {
        return count_clients.get();
    }

    public SimpleIntegerProperty count_clientsProperty() {
        return count_clients;
    }

    public void setCount_clients(int count_clients) {
        this.count_clients.set(count_clients);
    }

    public int getDay() {
        return day.get();
    }

    public SimpleIntegerProperty dayProperty() {
        return day;
    }

    public void setDay(int day) {
        this.day.set(day);
    }

    public String getFIOTrainer() {
        return FIOTrainer.get();
    }

    public SimpleStringProperty FIOTrainerProperty() {
        return FIOTrainer;
    }

    public void setFIOTrainer(String FIOTrainer) {
        this.FIOTrainer.set(FIOTrainer);
    }

    private void setFIOTrainer() {
        if (trainer.getName_trainer() == null) trainer.setName_trainer("");
        if (trainer.getPatronymic_trainer() == null) trainer.setPatronymic_trainer("");
        if (trainer.getSurname_trainer() == null) trainer.setSurname_trainer("");
        this.FIOTrainer.set(trainer.getSurname_trainer() + " " + trainer.getName_trainer() + " " + trainer.getPatronymic_trainer());
    }
}
