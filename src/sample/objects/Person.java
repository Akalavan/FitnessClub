package sample.objects;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.CheckBox;
import sample.enums.TypeTicket;

public class Person {
    private SimpleIntegerProperty tik_id = new SimpleIntegerProperty(0);
    private SimpleStringProperty tik_type = new SimpleStringProperty("null");
    private SimpleStringProperty name = new SimpleStringProperty("null");
    private SimpleStringProperty surname = new SimpleStringProperty("null");
    private SimpleStringProperty patronymic = new SimpleStringProperty("null");
    private SimpleBooleanProperty schedulePrivate = new SimpleBooleanProperty(false);
    private SimpleStringProperty dateSchedulePrivate = new SimpleStringProperty("null");
    private SimpleIntegerProperty group = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty countSubscription = new SimpleIntegerProperty(0);
    private SimpleStringProperty number = new SimpleStringProperty("null");
    private SimpleStringProperty mail = new SimpleStringProperty("null");
    private SimpleIntegerProperty stock = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty discount = new SimpleIntegerProperty(0);
    private SimpleMapProperty<Stock, StockStatus> stocks = new SimpleMapProperty<>(FXCollections.observableHashMap());
    private TypeTicket typeTicket;

    public Person() {
    }

    public Person(int tik_id, String tik_type, String name, String surname, String patronymic, int group, int schedulePrivate,
                  String dateSchedulePrivate) {
        this.tik_id = new SimpleIntegerProperty(tik_id);
        this.tik_type = new SimpleStringProperty(tik_type);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.group = new SimpleIntegerProperty(group);
        this.schedulePrivate = new SimpleBooleanProperty(!is(schedulePrivate));
        this.dateSchedulePrivate = new SimpleStringProperty(dateSchedulePrivate);
        typeTicket = type();
    }

    public ObservableMap<Stock, StockStatus> getStocks() {
        return stocks.get();
    }

    public SimpleMapProperty<Stock, StockStatus> stocksProperty() {
        return stocks;
    }

    public void setStocks(ObservableMap<Stock, StockStatus> stocks) {
        this.stocks.set(stocks);
    }

    //    public ObservableMap<Stock, Integer> getStocks() {
//        return stocks.get();
//    }
//
//    public SimpleMapProperty<Stock, Integer> stocksProperty() {
//        return stocks;
//    }
//
//    public void setStocks(ObservableMap<Stock, Integer> stocks) {
//        this.stocks.set(stocks);
//    }

    public int getDiscount() {
        return discount.get();
    }

    public SimpleIntegerProperty discountProperty() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount.set(discount);
    }

    public int getStock() {
        return stock.get();
    }

    public SimpleIntegerProperty stockProperty() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getMail() {
        return mail.get();
    }

    public SimpleStringProperty mailProperty() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public String getDateSchedulePrivate() {
        return dateSchedulePrivate.get();
    }

    public SimpleStringProperty dateSchedulePrivateProperty() {
        return dateSchedulePrivate;
    }

    public void setDateSchedulePrivate(String dateSchedulePrivate) {
        this.dateSchedulePrivate.set(dateSchedulePrivate);
    }

    private boolean is(int schedulePrivate) {
        return schedulePrivate == 0;
    }

    public boolean isSchedulePrivate() {
        return schedulePrivate.get();
    }

    public SimpleBooleanProperty schedulePrivateProperty() {
        return schedulePrivate;
    }

    public void setSchedulePrivate(boolean schedulePrivate) {
        this.schedulePrivate.set(schedulePrivate);
    }

    public void setTypeTicket(TypeTicket typeTicket) {
        this.typeTicket = typeTicket;
    }

    public int getTik_id() {
        return tik_id.get();
    }

    public SimpleIntegerProperty tik_idProperty() {
        return tik_id;
    }

    public void setTik_id(int tik_id) {
        this.tik_id.set(tik_id);
    }

    public String getTik_type() {
        return tik_type.get();
    }

    public SimpleStringProperty tik_typeProperty() {
        return tik_type;
    }

    public void setTik_type(String tik_type) {
        this.tik_type.set(tik_type);
    }

    public TypeTicket getTypeTicket() {
        return typeTicket;
    }

    public TypeTicket type() {
        TypeTicket typeTicket = null;
        switch (tik_type.get()) {
            case "Годовой":
                typeTicket = TypeTicket.ANNUAL;
                break;
            case "Месячный":
                typeTicket = TypeTicket.MONTHLY;
                break;
            case "Безлимитный":
                typeTicket = TypeTicket.UNLIMITED;
                break;
        }
        return typeTicket;
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

    public String getSurname() {
        return surname.get();
    }

    public SimpleStringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getPatronymic() {
        return patronymic.get();
    }

    public SimpleStringProperty patronymicProperty() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic.set(patronymic);
    }

    public int getGroup() {
        return group.get();
    }

    public SimpleIntegerProperty groupProperty() {
        return group;
    }

    public void setGroup(int group) {
        this.group.set(group);
    }

    public int getCountSubscription() {
        return countSubscription.get();
    }

    public SimpleIntegerProperty countSubscriptionProperty() {
        return countSubscription;
    }

    public void setCountSubscription(int countSubscription) {
        this.countSubscription.set(countSubscription);
    }

    @Override
    public String toString() {
        return "Person{" +
                "tik_id=" + tik_id +
                ", name=" + name +
                ", surname=" + surname +
                ", patronymic=" + patronymic +
                ", countSubscription=" + countSubscription +
                '}';
    }
}
