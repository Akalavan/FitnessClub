package sample.controllers.edits;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.FormatStringConverter;
import sample.controllers.SecondTabController;
import sample.controllers.ThirdTabController;
import sample.interfaces.impls.CollectionControlStock;
import sample.interfaces.impls.CollectionOperatingHall;
import sample.interfaces.impls.CollectionPersonBase;
import sample.objects.Person;
import sample.objects.Stock;
import sample.objects.StockPerson;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;


public class EditPersonalInfo extends Edit {
    @FXML
    public TextField textName;
    @FXML
    public TextField textSurname;
    @FXML
    public TextField textPatronymic;
    @FXML
    public ChoiceBox<String> choiceGroup;
    @FXML
    public Spinner SpinnerDate;
    @FXML
    public Button btnOk;
    @FXML
    public ChoiceBox<String> choiceDate;
    @FXML
    public ChoiceBox<String> choiceTick;
    @FXML
    public Button btnProd;
    @FXML
    public ChoiceBox<Integer> choiceStock;
    @FXML
    public TextField textMail;
    @FXML
    public TextField textNumber;
    @FXML
    public Label textPay;

    Person person;

    boolean b = false;

    public Person getPerson() {
        return person;
    }


    @FXML
    void initialize() {
        conSql();
        choiceTick.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (choiceTick.getSelectionModel().getSelectedItem() != null) {
                    textPay.setText("К оплате: " + CollectionOperatingHall.checkTipType(choiceTick.getSelectionModel().getSelectedItem() + "M"));
                    System.out.println(choiceTick.getSelectionModel().getSelectedItem());
                    choiceStock.getSelectionModel().clearSelection();
                }
            }
        });

        choiceStock.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (choiceTick.getSelectionModel().getSelectedItem() != null) {
                    int discount = 0, money = 0;
                    conSql();
                    String query = "select discount from stock WHERE id_stock = " + choiceStock.getSelectionModel().getSelectedItem();
                    try {
                        rs = stmt.executeQuery(query);
                        while (rs.next()) discount = rs.getInt(1);
                        money = CollectionOperatingHall.checkTipType(choiceTick.getSelectionModel().getSelectedItem() + "M");
                        textPay.setText("К оплате: " + (int) (money - Math.round(money * (discount / 100.0))));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    // textPay.setText("К оплате: " + CollectionOperatingHall.checkTipType(choiceTick.getSelectionModel().getSelectedItem()));
                }
            }
        });
    }

    private void conSql() {
        try {
            con = (Connection) DriverManager.getConnection(
                    URL + "?user=" + USER + "&password=" + PASSWORD +
                            "&useUnicode=true&characterEncoding=utf8");
            stmt = (Statement) con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPerson(Person person) {
        ObservableList<String> tik = FXCollections.observableArrayList(CollectionPersonBase.getGroup());
        ObservableList<String> tikType = FXCollections.observableArrayList(CollectionPersonBase.getType());
        choiceGroup.setItems(tik);
        choiceTick.setItems(tikType);
        ArrayList<String> arrayDate = new ArrayList<>();
        ObservableList<String> date;
        this.person = person;
        textName.setText(person.getName());
        textSurname.setText(person.getSurname());
        textPatronymic.setText(person.getPatronymic());
        textMail.setText(person.getMail());
        textNumber.setText(person.getNumber());
        choiceGroup.setValue(String.valueOf(person.getGroup()));
        choiceTick.setValue("");
        for (String s :
                person.getDateSchedulePrivate().split(";")) {
            arrayDate.add(s);
            //      System.out.println(s);
        }
        date = FXCollections.observableArrayList(arrayDate);
        // SpinnerDate.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<String>(date));
        choiceDate.setItems(date);
        choiceDate.setValue(arrayDate.get(0));
        StockRunnable stockRun = new StockRunnable(person);

    }

    public boolean isB() {
        return b;
    }

    public void actionButton(ActionEvent actionEvent) {

        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) return;

        Button btnClick = (Button) source;

        // if (choiceGroup.getSelectionModel().getSelectedItem() == null) return;
        // else {

        switch (btnClick.getId()) {
            case "btnOk":
                person.setName(textName.getText());
                person.setSurname(textSurname.getText());
                person.setPatronymic(textPatronymic.getText());
                person.setGroup(Integer.parseInt(choiceGroup.getSelectionModel().getSelectedItem()));
                person.setMail(textMail.getText());
                person.setNumber(textNumber.getText());

                conSql();
                // System.out.println(choiceTicket.getSelectionModel().getSelectedItem());
                // person.setTik_type(choiceGroup.getSelectionModel().getSelectedItem());
                b = true;
                actionClose(actionEvent);
                break;
            case "btnProd":
                conSql();
                String type = person.getTik_type();
                if (!(person.getCountSubscription() >= 2)) {
                    try {
                        if (!choiceStock.getSelectionModel().isEmpty()) {
                            person.setStock(choiceStock.getSelectionModel().getSelectedItem());
                            try {
                                String query = "select discount from stock WHERE id_stock = " + person.getStock();
                                rs = stmt.executeQuery(query);
                                while (rs.next()) person.setDiscount(rs.getInt(1));
                                query = "UPDATE `active_stock` SET `status_active` = '1' WHERE `active_stock`.`id_sport` = " + person.getTik_id();
                                stmt.executeUpdate(query);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

//                        LocalDate todayLocalDate = LocalDate.now( ZoneId.of( "America/Montreal" ) );
//                        java.sql.Date sqlDate = java.sql.Date.valueOf(todayLocalDate);
//                        stmt.executeUpdate("INSERT INTO `sport_tik` (`id_tik`, `id_sport`, `tik_type`, `date_salary`)" +
//                                "VALUES (NULL,'" + person.getTik_id() + "', '" + choiceTick.getSelectionModel().getSelectedItem() + "', '" +
//                                 todayLocalDate + "');");
                        }
                        person.setTik_type(choiceTick.getSelectionModel().getSelectedItem());
                        SecondTabController.getHallBaseSecondTable().sale(person);
                        JOptionPane.showMessageDialog(null, "Абонемент продлен");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Клиенту больше нельзя продлить");
                }
                person.setTik_type(type);
                break;
        }
        //   System.out.println(b);

        // }
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

    public ChoiceBox<String> getChoiceTicket() {
        return choiceGroup;
    }

    public void setB(boolean b) {
        this.b = b;
    }

    private class StockRunnable implements Runnable {
        Person person;
        Thread thread;

        StockRunnable(Person person) {
            this.person = person;
            thread = new Thread(this, "Поток");
            thread.start(); // Запускаем поток
        }

        @Override
        public void run() {
            System.out.println(this.getThread().getName());
            if (!CollectionControlStock.getStockObservableList().isEmpty() && person != null) {
                ObservableList<Integer> stocks = FXCollections.observableArrayList();
                for (Person stockPerson : CollectionControlStock.getStockObservableList()) {
                    System.out.println(stockPerson.getTik_id() + "; " + person.getTik_id());
                    if (stockPerson.getTik_id() == person.getTik_id()) {
                        for (Map.Entry stock : stockPerson.getStocks().entrySet()) {
                            System.out.println("OK");
                            System.out.println(stock.getKey() + " key");
                            stocks.add(Integer.valueOf(stock.getKey().toString()));
                        }
                    }
                }
                choiceStock.setItems(stocks);
            }

        }


        public Thread getThread() {
            return thread;
        }

        public void setThread(Thread thread) {
            this.thread = thread;
        }
    }
}
