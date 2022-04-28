package sample.controllers.chart;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ChartGroup extends Chart {

    @FXML
    public Label caption;
    @FXML
    public PieChart pieChart;
    @FXML
    public Button btnClear;

    @FXML
    void initialize() {
        try {
            con = (Connection) DriverManager.getConnection(
                    URL + "?user=" + USER + "&password=" + PASSWORD +
                            "&useUnicode=true&characterEncoding=utf8");
            stmt = (Statement) con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        date();

        pieChart.setLegendSide(Side.LEFT);
        pieChart.setStartAngle(30);

        caption.setTextFill(Color.WHITE);
        caption.setStyle("-fx-font: 12 arial;");

        for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX());
                    caption.setTranslateY(e.getSceneY() - 44);

                    caption.setText(String.valueOf(data.getPieValue()));
                }
            });
        }

    }

    void date() {
        HashMap<String, Integer> hashMap = new HashMap<>();

        try {
            rs = stmt.executeQuery("SELECT `id_group`, `count_Clients` FROM `group`");
            while (rs.next()) {
                hashMap.put("Группа № " + rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Map.Entry entry : hashMap.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: "
                    + entry.getValue());
            pieChart.getData().add(new PieChart.Data((String) entry.getKey(), (Integer) entry.getValue()));
        }
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void actionClear(ActionEvent actionEvent) {

    }

    public void actionClick(MouseEvent mouseEvent) {
    }
}
