package sample.interfaces.impls;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.interfaces.ControlTrainers;
import sample.objects.Hall;
import sample.objects.MoneyProfit;
import sample.objects.Trainer;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CollectionControlTrainers implements ControlTrainers {

    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private static ObservableList<Trainer> trainersList = FXCollections.observableArrayList();
    private static ArrayList<String> group = new ArrayList<>();

    public CollectionControlTrainers() {
        conSql();
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

    public static ObservableList<Trainer> getTrainersList() {
        return trainersList;
    }

    public void date() {
        String groupTrainer;
        String query = "SELECT id_trainers, name_trainer, surname_trainer, " +
                "patronymic_trainer, salary_trainer, date_hired FROM trainers WHERE id_trainers > 0";
        String query1 = "SELECT id_group FROM `group`";

        // System.out.println(query);
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id_trainers = rs.getInt(1);
                String name_trainer = rs.getString(2);
                String surname_trainer = rs.getString(3);
                String patronymic_trainer = rs.getString(4);
                int salary_trainer = rs.getInt(5);
                LocalDate date_transactions = rs.getObject(6, LocalDate.class);
                String query2 = "SELECT `id_group` FROM `group` WHERE `id_trainer` = " + id_trainers;
                groupTrainer = getValue(query2);
                System.out.printf("id_trainers: %d, name_trainer: %s, surname_trainer: %s, patronymic_trainer: %s," +
                                " salary_trainer: %d, date_transactions: %s, group: %s %n", id_trainers, name_trainer,
                        surname_trainer, patronymic_trainer, salary_trainer, date_transactions, groupTrainer);
                trainersList.add(new Trainer(id_trainers, name_trainer, surname_trainer,
                        patronymic_trainer, salary_trainer, String.valueOf(date_transactions), groupTrainer));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            rs = stmt.executeQuery(query1);
            group.add("Н/А");
            while (rs.next()) {
                String idGroup = rs.getString(1);
                group.add(idGroup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getValue(String query) {
        String str = null;
        ResultSet rs1;
        Statement stmt1;
        try {
            stmt1 = (Statement) con.createStatement();
            rs1 = stmt1.executeQuery(query);
            while (rs1.next()) {
                str = rs1.getString(1);
            }
            rs1.close();
            stmt1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public void add(Trainer trainer) {
        conSql();
        System.out.println("addT");
        String group = "0";
        if (!(trainer.getGroup().equals("Н/А"))) group = trainer.getGroup();
        String max = "SELECT MAX(`id_trainers`) FROM `trainers`";
        int maxId = 0;
        try {
            rs = stmt.executeQuery(max);
            while (rs.next()) {
                maxId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String query = "INSERT INTO `testdb`.`trainers` (`id_trainers`, `name_trainer`, `surname_trainer`, " +
                "`patronymic_trainer`, `salary_trainer`, `date_hired`) VALUES ('" + (maxId + 1) + "', '" + trainer.getName_trainer() +
                "', '" + trainer.getSurname_trainer() + "', " + "'" + trainer.getPatronymic_trainer() + "', '" +
                trainer.getSalary_trainer() + "', '" + trainer.getDate_hired() + "');";
//        INSERT INTO `testdb`.`sport`
//        (`tik_type`, `name`, `surname`, `patronymic`) VALUES ('Безлимитный', 'Сергей', 'Петров', 'Олегович');
        System.out.println(query);
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        trainersList.add(trainer);
    }

    @Override
    public void sack() {

    }

    @Override
    public void assignGroup() {

    }

    public static ArrayList<String> getGroup() {
        return group;
    }
}
