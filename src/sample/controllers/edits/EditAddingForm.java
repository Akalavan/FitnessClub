package sample.controllers.edits;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.interfaces.impls.CollectionPersonBase;
import sample.objects.Person;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EditAddingForm extends Edit {
    @FXML
    public TextField textName;
    @FXML
    public TextField textSurname;
    @FXML
    public TextField textPatronymic;
    @FXML
    public ChoiceBox<String> choiceTicket;
    @FXML
    public Button btnOk;
    @FXML
    public TextField textMail;
    @FXML
    public TextField textNumber;

    Person person;

    boolean b = false;

    public Person getPerson() {
        return person;
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

    @FXML
    void initialize() {
        ObservableList<String> tik = FXCollections.observableArrayList(CollectionPersonBase.getType());
        choiceTicket.setItems(tik);
        conSql();
    }


    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isB() {
        return b;
    }

    public void actionButton(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) return;

        Button btnClick = (Button) source;

        if (choiceTicket.getSelectionModel().getSelectedItem() == null) return;
        else {
            int count = 0;
            switch (btnClick.getId()) {
                case "btnOk":
                    conSql();
                    try {
                        String query = "SELECT COUNT(tik_id) FROM `sport` WHERE name ='" + textName.getText() + "' and surname ='" +
                                textSurname.getText() + "' and patronymic ='" + textPatronymic.getText() + "'";
                        System.out.println(query);
                        rs = stmt.executeQuery(query);

                        while (rs.next()) count = rs.getInt(1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if (count == 0) {
                        person.setName(textName.getText());
                        person.setSurname(textSurname.getText());
                        person.setPatronymic(textPatronymic.getText());
                        // System.out.println(choiceTicket.getSelectionModel().getSelectedItem());
                        person.setTik_type(choiceTicket.getSelectionModel().getSelectedItem());
                        person.setNumber(textNumber.getText());
                        person.setMail(textMail.getText());
                        b = true;
                        JOptionPane.showMessageDialog(null, "Клиент добавлен");
                    } else {
                        JOptionPane.showMessageDialog(null, "Такой клиент уже есть");
                    }
                    break;
            }
            //   System.out.println(b);
            if (count == 0) {
                actionClose(actionEvent);
            }
        }
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
        return choiceTicket;
    }

    public void setB(boolean b) {
        this.b = b;
    }
}
