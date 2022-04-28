package sample.controllers.chart;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.controllers.FirstTabController;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChoiceChartController extends Chart {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private static ArrayList<String> group = new ArrayList<>();

    @FXML
    public DatePicker dateStart;
    @FXML
    public DatePicker dateEnd;
    @FXML
    public ChoiceBox<String> choiceRep;
    @FXML
    public Button btnOk;

    private Stage main;

    @FXML
    private void initialize() {

        try {
            con = (Connection) DriverManager.getConnection(
                    URL + "?user=" + USER + "&password=" + PASSWORD +
                            "&useUnicode=true&characterEncoding=utf8");
            stmt = (Statement) con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        dateStart.setConverter(converter);
        dateStart.setPromptText("yyyy-MM-dd");
        dateEnd.setConverter(converter);
        dateEnd.setPromptText("yyyy-MM-dd");

        try {
            rs = stmt.executeQuery("SHOW TABLES FROM testdb");
            while (rs.next()) {
                if (rs.getString(1).equals("group") || rs.getString(1).equals("sport_tik") || rs.getString(1).equals("money_profit"))
                    group.add(rs.getString(1));
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        choiceRep.setItems(FXCollections.observableArrayList(group));
        dateStart.setDisable(true);
        dateEnd.setDisable(true);
        btnOk.setDisable(true);
        initListeners();
    }

    private Stage reg(Stage stage, String path, String title, int width, int height) {
        stage = null;
        try {
            fxmlLoader = new FXMLLoader();
            fxmlRep = null;
            chartsController = null;
            fxmlLoader.setLocation(getClass().getResource(path));
            fxmlRep = fxmlLoader.load();
            chartsController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage();
        stage.setTitle(title);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(false);
        stage.setScene(new Scene(fxmlRep));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(main);
        return stage;
    }

    private void initListeners() {
        choiceRep.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> selected, String oldFruit, String newFruit) {
                if (newFruit != null) {
                    switch (newFruit) {
                        case "group":
                            dateStart.setDisable(true);
                            dateEnd.setDisable(true);
                            btnOk.setDisable(false);
                            break;
                        case "sport_tik":
                            dateStart.setDisable(false);
                            dateEnd.setDisable(false);
                            btnOk.setDisable(false);
                            break;
                        case "money_profit":
                            dateStart.setDisable(false);
                            dateEnd.setDisable(false);
                            btnOk.setDisable(false);
                            break;
                        default:
                            btnOk.setDisable(true);
                    }
                }
            }
        });

    }

    private void showDialog(Stage stage) {
        stage.show();
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) return;

        Button btnClick = (Button) source;

        switch (choiceRep.getSelectionModel().getSelectedItem()) {
            case "group":
                editReport = reg(editReport, "../../fxml/chartGroup.fxml", "Отчёт по группам", 300, 101);
                showDialog(editReport);
                break;
            case "hall":
                break;
            case "money_lost":
                break;
            case "money_profit":
                setDateStart(dateStart.getEditor().getText());
                setDateEnd(dateEnd.getEditor().getText());
                editReport = reg(editReport, "../../fxml/charts.fxml", "Отчёт по заработку", 300, 101);
                chartsController.setChoice("money_profit");
                chartsController.setDate();
                showDialog(editReport);
                break;
            case "people_group":
                break;
            case "schedule_group":
                break;
            case "schedule_private":
                break;
            case "sport":
                break;
            case "sport_tik":
                setDateStart(dateStart.getEditor().getText());
                setDateEnd(dateEnd.getEditor().getText());
                editReport = reg(editReport, "../../fxml/charts.fxml", "Отчёт по спортсменам", 300, 101);
                chartsController.setChoice("sport_tik");
                chartsController.setDate();
                showDialog(editReport);
                break;
            case "trainers":
                break;
            case "type_ticket":
                break;

        }
    }

    public DatePicker getDateStart() {
        return dateStart;
    }

    public DatePicker getDateEnd() {
        return dateEnd;
    }

    public void setMain(Stage main) {
        this.main = main;
    }
}
