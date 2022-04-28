package sample.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.time.LocalDate;
import java.util.HashMap;

public class Stock {
    private static int i = 1;
    private SimpleIntegerProperty id_stock = new SimpleIntegerProperty(++i);
    private SimpleStringProperty dateStart = new SimpleStringProperty("null");
    private SimpleStringProperty dateEnd = new SimpleStringProperty("null");
    private SimpleStringProperty condition = new SimpleStringProperty("null");
    private SimpleStringProperty comment = new SimpleStringProperty("null");
    private SimpleMapProperty<Person, LocalDate> idPersonDate = new SimpleMapProperty<>(FXCollections.observableHashMap());

    public Stock(String dateStart, String dateEnd, String condition, String comment) {
        this.dateStart = new SimpleStringProperty(dateStart);
        this.dateEnd = new SimpleStringProperty(dateEnd);
        this.condition = new SimpleStringProperty(condition);
        this.comment = new SimpleStringProperty(comment);
    }

    public int getId_stock() {
        return id_stock.get();
    }

    public SimpleIntegerProperty id_stockProperty() {
        return id_stock;
    }

    public void setId_stock(int id_stock) {
        this.id_stock.set(id_stock);
    }

    public String getDateStart() {
        return dateStart.get();
    }

    public SimpleStringProperty dateStartProperty() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart.set(dateStart);
    }

    public String getDateEnd() {
        return dateEnd.get();
    }

    public SimpleStringProperty dateEndProperty() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd.set(dateEnd);
    }

    public String getCondition() {
        return condition.get();
    }

    public SimpleStringProperty conditionProperty() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition.set(condition);
    }

    public String getComment() {
        return comment.get();
    }

    public SimpleStringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment.set(comment);
    }

    public ObservableMap<Person, LocalDate> getIdPersonDate() {
        return idPersonDate.get();
    }

    public SimpleMapProperty<Person, LocalDate> idPersonDateProperty() {
        return idPersonDate;
    }

    public void setIdPersonDate(ObservableMap<Person, LocalDate> idPersonDate) {
        this.idPersonDate.set(idPersonDate);
    }

    @Override
    public String toString() {
        return String.valueOf(id_stock.get());
    }
}
