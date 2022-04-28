package sample.objects;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import sample.enums.TypeTicket;

public class StockPerson {

    private SimpleIntegerProperty tik_id = new SimpleIntegerProperty(0);
    private SimpleStringProperty name = new SimpleStringProperty("null");
    private SimpleStringProperty surname = new SimpleStringProperty("null");
    private SimpleStringProperty patronymic = new SimpleStringProperty("null");
    private SimpleStringProperty number = new SimpleStringProperty("null");
    private SimpleIntegerProperty stock = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty countTik = new SimpleIntegerProperty(0);
    private SimpleStringProperty email = new SimpleStringProperty(null);
    private SimpleBooleanProperty status = new SimpleBooleanProperty(false);


    public StockPerson(int tik_id, int stock, int countTik, String name, String surname, String patronymic,
                       String number, String email, Boolean status) {
        this.tik_id = new SimpleIntegerProperty(tik_id);
        this.stock = new SimpleIntegerProperty(stock);
        this.countTik = new SimpleIntegerProperty(countTik);
//        this.tik_type = new SimpleStringProperty(tik_type);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.patronymic = new SimpleStringProperty(patronymic);
        this.number = new SimpleStringProperty(number);
        this.email = new SimpleStringProperty(email);
        this.status = new SimpleBooleanProperty(status);
//        this.group = new SimpleIntegerProperty(group);
//        this.schedulePrivate = new SimpleBooleanProperty(!is(schedulePrivate));
//        this.dateSchedulePrivate = new SimpleStringProperty(dateSchedulePrivate);
//        typeTicket = type();
    }

//    public String getDateSchedulePrivate() {
//        return dateSchedulePrivate.get();
//    }
//
//    public SimpleStringProperty dateSchedulePrivateProperty() {
//        return dateSchedulePrivate;
//    }
//
//    public void setDateSchedulePrivate(String dateSchedulePrivate) {
//        this.dateSchedulePrivate.set(dateSchedulePrivate);
//    }
//
//    private boolean is(int schedulePrivate) {
//        return schedulePrivate == 0;
//    }
//
//    public boolean isSchedulePrivate() {
//        return schedulePrivate.get();
//    }
//
//    public SimpleBooleanProperty schedulePrivateProperty() {
//        return schedulePrivate;
//    }
//
//    public void setSchedulePrivate(boolean schedulePrivate) {
//        this.schedulePrivate.set(schedulePrivate);
//    }
//
//    public void setTypeTicket(TypeTicket typeTicket) {
//        this.typeTicket = typeTicket;
//    }

    public int getTik_id() {
        return tik_id.get();
    }

    public SimpleIntegerProperty tik_idProperty() {
        return tik_id;
    }

    public void setTik_id(int tik_id) {
        this.tik_id.set(tik_id);
    }

    public String getEmail() {
        return email.get();
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public boolean isStatus() {
        return status.get();
    }

    public SimpleBooleanProperty statusProperty() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status.set(status);
    }

    //    public String getTik_type() {
//        return tik_type.get();
//    }
//
//    public SimpleStringProperty tik_typeProperty() {
//        return tik_type;
//    }
//
//    public void setTik_type(String tik_type) {
//        this.tik_type.set(tik_type);
//    }
//
//    public TypeTicket getTypeTicket() {
//        return typeTicket;
//    }
//
//    public TypeTicket type() {
//        TypeTicket typeTicket = null;
//        switch (tik_type.get()) {
//            case "Годовой":
//                typeTicket = TypeTicket.ANNUAL;
//                break;
//            case "Месячный":
//                typeTicket = TypeTicket.MONTHLY;
//                break;
//            case "Безлимитный":
//                typeTicket = TypeTicket.UNLIMITED;
//                break;
//        }
//        return typeTicket;
//    }
//
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

    public String getNumber() {
        return number.get();
    }

    public SimpleStringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    //
//    public int getGroup() {
//        return group.get();
//    }
//
//    public SimpleIntegerProperty groupProperty() {
//        return group;
//    }
//
//    public void setGroup(int group) {
//        this.group.set(group);
//    }

    public int getStock() {
        return stock.get();
    }

    public SimpleIntegerProperty stockProperty() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }

    public int getCountTik() {
        return countTik.get();
    }

    public SimpleIntegerProperty countTikProperty() {
        return countTik;
    }

    public void setCountTik(int countTik) {
        this.countTik.set(countTik);
    }
}
