package sample.start;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.connectSQL.SQL;
import sample.controllers.Controller;
import sample.interfaces.impls.CollectionPersonBase;
import sample.objects.Log;
import sample.objects.Operation;
import sample.objects.User;

import javax.swing.*;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Main extends Application {


    private static User userLogin;

    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private static Stage main;

    private FXMLLoader fxmlLoader = new FXMLLoader();
    private Controller controller = new Controller();
    private Parent root;

    @FXML
    ChoiceBox<User> choiceUser;
    @FXML
    TextField editPassword;
    private ArrayList<User> users = new ArrayList<>();
    private static HashMap<String, Operation> operation = new HashMap<>();

    @FXML
    void initialize() {
        SQL.getInstance();
        readOperation();
        ObservableList<User> user = FXCollections.observableArrayList(readUser());
        choiceUser.setItems(user);
        choiceUser.getSelectionModel().select(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        main = primaryStage;
        fxmlLoader.setLocation(getClass().getResource("../fxml/fxmlLogin.fxml"));
        root = fxmlLoader.load();
        primaryStage.setTitle("Вход");
        primaryStage.setScene(new Scene(root));
        // controller.setMainStage(primaryStage);
        System.out.println(primaryStage.getTitle());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Stage getMain() {
        return main;
    }

    public static void main(String[] args) {
        launch(args);
    }


    private ArrayList<User> readUser() {
        ArrayList<String> loginUsers = new ArrayList<>();
        String query = "SELECT * FROM `testdb`.`users`;";
        conSql();
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id_user = rs.getInt(1);
                String name = rs.getString(2);
                String surname = rs.getString(3);
                String patronymic = rs.getString(4);
                String password = rs.getString(5);
                users.add(new User(id_user, name, surname, patronymic, password));
                //   loginUsers.add(surname + " " + name + " " + patronymic);
            }
        } catch (SQLException e) {

        }
        return users;
    }

    private void readOperation() {
        String query = "SELECT * FROM `testdb`.`operation`;";
        conSql();
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id_operation = rs.getInt(1);
                String name = rs.getString(2);
                String comment = rs.getString(3);
                operation.put(name, new Operation(id_operation, name, comment));
                //   loginUsers.add(surname + " " + name + " " + patronymic);
            }
        } catch (SQLException e) {

        }
    }

    public void actionButton(ActionEvent actionEvent) throws IOException {
        if (login(choiceUser.getSelectionModel().getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "Пароль неверный", "Ошибка", JOptionPane.WARNING_MESSAGE);
        } else {
            userLogin = choiceUser.getSelectionModel().getSelectedItem();
            //  new Log(operation.get("login").getId_operation(), userLogin.getId_user());
            try {
                actionClose(actionEvent);
                stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../fxml/sample.fxml"));
            root = fxmlLoader.load();
            controller = new Controller();
            main.setTitle("ФИТНЕС КЛУБ");
            main.setScene(new Scene(root, 800, 450));
            // controller.setMainStage(primaryStage);
            System.out.println(main.getTitle());
            main.setResizable(true);
            main.show();
        }
    }


    private boolean login(User selectedItem) throws IOException {
        boolean ok = true;
        String inputHash = "";
        System.out.print("Now try to enter a password : ");
        try {
            inputHash = byteArrayToHexString(Main.computeHash(editPassword.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (selectedItem.getPassword().equals(inputHash)) {
            System.out.println("You got it!");
            ok = false;
        } else
            System.out.println("Wrong, try again...!");
        //return ok;
        return false;
    }

    private static byte[] computeHash(String x)
            throws Exception {
        java.security.MessageDigest d = null;
        d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(x.getBytes());
        return d.digest();
    }

    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    private static void conSql() {
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
    public void stop() throws Exception {
        System.out.println("Application stops");
        if (main.getTitle().equals("ФИТНЕС КЛУБ"))
            new Log(operation.get("logout").getId_operation(), userLogin.getId_user());
        super.stop();
    }
}
