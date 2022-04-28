package sample.objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.ZoneId;

public class StockStatus {
    private static int i = 0;
    private SimpleIntegerProperty id_Status = new SimpleIntegerProperty(++i);
    private SimpleBooleanProperty sandMail = new SimpleBooleanProperty(false);
    private SimpleBooleanProperty active = new SimpleBooleanProperty(false);
    private SimpleIntegerProperty countStock = new SimpleIntegerProperty(1);
    private SimpleStringProperty dateBird = new SimpleStringProperty("null");
    private SimpleStringProperty dateEnd = new SimpleStringProperty("null");

    public String getDateBird() {
        return dateBird.get();
    }

    public SimpleStringProperty dateBirdProperty() {
        return dateBird;
    }

    public void setDateBird(String dateBird) {
        this.dateBird.set(dateBird);
    }

    public int getId_Status() {
        return id_Status.get();
    }

    public SimpleIntegerProperty id_StatusProperty() {
        return id_Status;
    }

    public void setId_Status(int id_Status) {
        this.id_Status.set(id_Status);
    }

    public boolean isSandMail() {
        return sandMail.get();
    }

    public SimpleBooleanProperty sandMailProperty() {
        return sandMail;
    }

    public void setSandMail(boolean sandMail) {
        this.sandMail.set(sandMail);
    }

    public boolean isActive() {
        return active.get();
    }

    public SimpleBooleanProperty activeProperty() {
        return active;
    }

    public void setActive(boolean active) {
        this.active.set(active);
    }

    public int getCountStock() {
        return countStock.get();
    }

    public SimpleIntegerProperty countStockProperty() {
        return countStock;
    }

    public void setCountStock(int countStock) {
        this.countStock.set(countStock);
    }

    public void setCountStock(String s) {
        this.dateBird.set(s);
        this.countStock.set(countStock.get() + 1);
    }

    @Override
    public String toString() {
        return String.valueOf(countStock.get());
    }
}
