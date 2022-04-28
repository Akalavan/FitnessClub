package sample.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Group {

    private Trainer trainer;

    private SimpleIntegerProperty id = new SimpleIntegerProperty(0);
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty FIOTrainer = new SimpleStringProperty("");
    private SimpleIntegerProperty count_clients = new SimpleIntegerProperty(0);

    public Group(int id, String name, Trainer trainer, int count_clients) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.trainer = trainer;
        setFIOTrainer();
        this.count_clients = new SimpleIntegerProperty(count_clients);
    }

    private void setFIOTrainer() {
        if (trainer.getName_trainer() == null) trainer.setName_trainer("");
        if (trainer.getPatronymic_trainer() == null) trainer.setPatronymic_trainer("");
        if (trainer.getSurname_trainer() == null) trainer.setSurname_trainer("");
        this.FIOTrainer.set(trainer.getSurname_trainer() + " " + trainer.getName_trainer() + " " + trainer.getPatronymic_trainer());
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public void setTrainer(Trainer trainer) {
        this.trainer = trainer;
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

    public String getFIOTrainer() {
        return FIOTrainer.get();
    }

    public SimpleStringProperty FIOTrainerProperty() {
        return FIOTrainer;
    }

    public void setFIOTrainer(String FIOTrainer) {
        this.FIOTrainer.set(FIOTrainer);
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
}
