package sample.controllers.chart;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.interfaces.impls.CollectionPersonBase;
import sample.objects.Person;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ChartsController extends Chart {
    @FXML
    public Button btnClear;
    public Label caption;

    private HashMap<Integer, String> months = new HashMap<>();
    //private String dateStart, dateEnd;
    @FXML
    public BarChart<String, Number> barChart;

    private CollectionPersonBase personBaseFirstTable;

    {
        months.put(1, "Январь");
        months.put(2, "Февраль");
        months.put(3, "Март");
        months.put(4, "Апрель");
        months.put(5, "Май");
        months.put(6, "Июнь");
        months.put(7, "Июль");
        months.put(8, "Август");
        months.put(9, "Сентябрь");
        months.put(10, "Октябрь");
        months.put(11, "Ноябрь");
        months.put(12, "Декабрь");
    }

    public void setDate() {
        String query, str;
        int month;
        int dateE, dateS;
        String s[];

        s = dateStart.split("-");
        dateS = Integer.parseInt(s[0] + s[1]);
        s = dateEnd.split("-");
        dateE = Integer.parseInt(s[0] + s[1]);

        do {
            str = String.valueOf(dateS);
            str = str.substring(str.length() - 2, str.length());
            System.out.println(str);
            month = Integer.parseInt(str);
            s = dateStart.split("-");
            dateEnd = s[0] + "-" + str + "-" + 31;
            //System.out.println(dateEnd);
            if (choice.equals("sport_tik"))
                query = "SELECT `date_salary` FROM `sport_tik` WHERE DATE(`date_salary`) >= '" + dateStart + "'  and " +
                        "DATE(`date_salary`) <= '" + dateEnd + "'";
            else
                query = "SELECT `date_transactions`, `money_as_much` FROM `money_profit` " +
                        "WHERE DATE(`date_transactions`) >= '" + dateStart + "'  and DATE(`date_transactions`) <= '" + dateEnd + "'";
            date(query, month);
            dateStart = dateEnd;
            if (str.equals("12")) {
                dateS += 100;
                dateS -= 12;
            }
            if (dateS == dateE) break;
            dateS++;
        } while (true);
        for (final XYChart.Series<String, Number> series : barChart.getData()) {
            for (XYChart.Data<String, Number> data : series.getData()) {
                data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                        Number yValue = data.getYValue();
                        caption.setTranslateX(e.getSceneX() - 20);
                        caption.setTranslateY(e.getSceneY() - 44);

                        caption.setText(String.valueOf(yValue));
                        System.out.println(yValue);
                    }
                });
            }
        }
    }

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
        barChart.getXAxis().setLabel("People");
        barChart.getYAxis().setLabel("Number");
        barChart.setTitle("Some Programming Languages");

        caption.setTextFill(Color.WHITE);
        caption.setStyle("-fx-font: 12 arial;");
    }

    void date(String query, int month) {
        int k = 0;
        XYChart.Series<String, Number> dataSeries1 = new XYChart.Series<String, Number>();
        //dataSeries1.setName("people");
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                if (choice.equals("sport_tik")) k++;
                else if (choice.equals("money_profit")) k += rs.getInt(2);
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(months.get(month) + k);
        dataSeries1.getData().add(new XYChart.Data<String, Number>(months.get(month), k));
        barChart.getData().add(dataSeries1);
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void setPersonBaseFirstTable(CollectionPersonBase personBaseFirstTable) {
        this.personBaseFirstTable = personBaseFirstTable;
    }


    public void actionClear(ActionEvent actionEvent) {
        barChart.getData().clear();
    }
}
