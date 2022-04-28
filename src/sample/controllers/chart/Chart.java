package sample.controllers.chart;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class Chart {
    static final String URL = "jdbc:mysql://localhost:3306/testdb";
    static final String USER = "root";
    static final String PASSWORD = "";

    static Connection con;
    static Statement stmt;
    static ResultSet rs;

    FXMLLoader fxmlLoader = new FXMLLoader();
    Chart chartsController;
    Parent fxmlRep;
    Stage editReport;
    Stage mainStage;
    String choice;

    protected static String dateStart, dateEnd;

    public Chart() {
        //System.out.println(dateStart);
    }

    public void setDate() {

    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public static void setDateStart(String dateStart) {
        Chart.dateStart = dateStart;
    }

    public static void setDateEnd(String dateEnd) {
        Chart.dateEnd = dateEnd;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
