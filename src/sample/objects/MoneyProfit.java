package sample.objects;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class MoneyProfit {
    private static int i = 0;
    private final SimpleIntegerProperty id_transactions = new SimpleIntegerProperty(++i);
    private SimpleIntegerProperty id_sport = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty money_as_much = new SimpleIntegerProperty(0);
    private SimpleStringProperty date_transactions = new SimpleStringProperty();
    private SimpleStringProperty date_end_subscription = new SimpleStringProperty();
    private SimpleIntegerProperty id_hall = new SimpleIntegerProperty(0);

    public MoneyProfit(int id_hall, int money_as_much, int id_sport, String date_transactions, String date_end_subscription) {
        this.money_as_much = new SimpleIntegerProperty(money_as_much);
        this.id_sport = new SimpleIntegerProperty(id_sport);
        this.date_transactions = new SimpleStringProperty(date_transactions);
        this.date_end_subscription = new SimpleStringProperty(date_end_subscription);
        this.id_hall = new SimpleIntegerProperty(id_hall);
    }

    public int getId_sport() {
        return id_sport.get();
    }

    public SimpleIntegerProperty id_sportProperty() {
        return id_sport;
    }

    public void setId_sport(int id_sport) {
        this.id_sport.set(id_sport);
    }

    public int getId_transactions() {
        return id_transactions.get();
    }

    public SimpleIntegerProperty id_transactionsProperty() {
        return id_transactions;
    }

    public void setId_transactions(int id_transactions) {
        this.id_transactions.set(id_transactions);
    }

    public int getMoney_as_much() {
        return money_as_much.get();
    }

    public SimpleIntegerProperty money_as_muchProperty() {
        return money_as_much;
    }

    public void setMoney_as_much(int money_as_much) {
        this.money_as_much.set(money_as_much);
    }

    public String getDate_transactions() {
        return date_transactions.get();
    }

    public SimpleStringProperty date_transactionsProperty() {
        return date_transactions;
    }

    public void setDate_transactions(String date_transactions) {
        this.date_transactions.set(date_transactions);
    }

    public int getId_hall() {
        return id_hall.get();
    }

    public SimpleIntegerProperty id_hallProperty() {
        return id_hall;
    }

    public void setId_hall(int id_hall) {
        this.id_hall.set(id_hall);
    }
}
