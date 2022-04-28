package sample.controllers.edits;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.sql.ResultSet;

public abstract class Edit {
    static final String URL = "jdbc:mysql://localhost:3306/testdb";
    static final String USER = "root";
    static final String PASSWORD = "";

    static Connection con;
    static Statement stmt;
    static ResultSet rs;

    @FXML
    abstract void initialize();

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    abstract void actionButton(ActionEvent actionEvent);
}
