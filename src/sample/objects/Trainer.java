package sample.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Trainer {
    //private static int i = 0;
    private SimpleIntegerProperty id_trainers = new SimpleIntegerProperty(0);
    private SimpleStringProperty name_trainer = new SimpleStringProperty("");
    private SimpleStringProperty surname_trainer = new SimpleStringProperty("");
    private SimpleStringProperty patronymic_trainer = new SimpleStringProperty("");
    private SimpleIntegerProperty salary_trainer = new SimpleIntegerProperty(0);
    private SimpleStringProperty date_hired = new SimpleStringProperty("");
    private SimpleStringProperty group = new SimpleStringProperty("");

    public Trainer() {
    }

    public Trainer(int id_trainers, String name_trainer, String surname_trainer, String patronymic_trainer,
                   int salary_trainer, String date_hired, String group) {
        this.id_trainers = new SimpleIntegerProperty(id_trainers);
        this.name_trainer = new SimpleStringProperty(name_trainer);
        this.surname_trainer = new SimpleStringProperty(surname_trainer);
        this.patronymic_trainer = new SimpleStringProperty(patronymic_trainer);
        this.salary_trainer = new SimpleIntegerProperty(salary_trainer);
        this.date_hired = new SimpleStringProperty(date_hired);
        this.group = new SimpleStringProperty(group);
    }

    public int getId_trainers() {
        return id_trainers.get();
    }

    public SimpleIntegerProperty id_trainersProperty() {
        return id_trainers;
    }

    public void setId_trainers(int id_trainers) {
        this.id_trainers.set(id_trainers);
    }

    public String getName_trainer() {
        return name_trainer.get();
    }

    public SimpleStringProperty name_trainerProperty() {
        return name_trainer;
    }

    public void setName_trainer(String name_trainer) {
        this.name_trainer.set(name_trainer);
    }

    public String getSurname_trainer() {
        return surname_trainer.get();
    }

    public SimpleStringProperty surname_trainerProperty() {
        return surname_trainer;
    }

    public void setSurname_trainer(String surname_trainer) {
        this.surname_trainer.set(surname_trainer);
    }

    public String getPatronymic_trainer() {
        return patronymic_trainer.get();
    }

    public SimpleStringProperty patronymic_trainerProperty() {
        return patronymic_trainer;
    }

    public void setPatronymic_trainer(String patronymic_trainer) {
        this.patronymic_trainer.set(patronymic_trainer);
    }

    public int getSalary_trainer() {
        return salary_trainer.get();
    }

    public SimpleIntegerProperty salary_trainerProperty() {
        return salary_trainer;
    }

    public void setSalary_trainer(int salary_trainer) {
        this.salary_trainer.set(salary_trainer);
    }

    public String getDate_hired() {
        return date_hired.get();
    }

    public SimpleStringProperty date_hiredProperty() {
        return date_hired;
    }

    public void setDate_hired(String date_hired) {
        this.date_hired.set(date_hired);
    }

    public String getGroup() {
        return group.get();
    }

    public SimpleStringProperty groupProperty() {
        return group;
    }

    public void setGroup(String group) {
        this.group.set(group);
    }

    @Override
    public String toString() {
        return "name_trainer=" + name_trainer +
                ", surname_trainer=" + surname_trainer +
                ", patronymic_trainer=" + patronymic_trainer;
    }
}
