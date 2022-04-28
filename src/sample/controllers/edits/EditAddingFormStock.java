package sample.controllers.edits;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import sample.interfaces.impls.CollectionAllStack;
import sample.interfaces.impls.CollectionPersonBase;
import sample.objects.Stock;

import javax.swing.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static sample.interfaces.impls.CollectionControlStock.getTypeTikInt;
import static sample.interfaces.impls.CollectionOperatingHall.checkTipType;

public class EditAddingFormStock extends Edit {
    @FXML
    public Button btnAccess;
    @FXML
    public Button btnCancel;
    @FXML
    public TextField sum;
    @FXML
    public TextField countSub;
    @FXML
    public TextArea textStock;
    @FXML
    public DatePicker dateEnd;
    @FXML
    public ChoiceBox<String> boxType;

    @Override
    void initialize() {
        ObservableList<String> tikType = FXCollections.observableArrayList(CollectionPersonBase.getType());
        boxType.setItems(tikType);
        concertFormDate();
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

    @Override
    public void actionButton(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (!(source instanceof Button)) return;

        Button btn = (Button) source;

        switch (btn.getId()) {
            case "btnAccess":
                if (!countSub.getText().equals("") && !sum.getText().equals("") && !textStock.getText().equals("") &&
                        !dateEnd.getEditor().getText().equals("")) {
                    LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));
                    String condition = "";
                    if (!boxType.getSelectionModel().isEmpty()) {
                        int typeInt = getTypeTikInt(boxType.getSelectionModel().getSelectedItem());
                        ;
                        condition = countSub.getText() + ";" + sum.getText() + ";" + typeInt;
                    } else condition = countSub.getText() + ";" + sum.getText();
                    System.out.println(condition);
                    Stock stock = new Stock(String.valueOf(dateNow), dateEnd.getEditor().getText(),
                            condition, textStock.getText());
                    addStock(stock);
                } else JOptionPane.showMessageDialog(null, "Не все поля заполнены!", "Акция", 2);

                break;
            case "btnCancel":
                actionClose(actionEvent);
                break;
        }


    }

    private void addStock(Stock stock) {
        conSql();
        String query = "INSERT INTO `stock` (`id_stock`, `date_start`, `date_end`, `condition`, `comment`) VALUES (NULL, '" + stock.getDateStart() +
                "', '" + stock.getDateEnd() + "', '" + stock.getCondition() + "', '" + stock.getComment() + "')";
        try {
            stmt.executeUpdate(query);
            CollectionAllStack.getAllStockList().add(stock);
            JOptionPane.showMessageDialog(null, "Акция успешно дабавленна");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void concertFormDate() {
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        dateEnd.setConverter(converter);
        dateEnd.setPromptText("yyyy-MM-dd");
    }

    public TextField getSum() {
        return sum;
    }

    public TextField getCountSub() {
        return countSub;
    }

}
