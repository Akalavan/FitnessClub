package sample.objects;

import javafx.util.StringConverter;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Log {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private int id_operation;
    private int id_user;
    private LocalDateTime today = LocalDateTime.of(LocalDate.now(), LocalTime.now());

    public Log(int id_operation, int id_user) {
        this.id_operation = id_operation;
        this.id_user = id_user;
        recordLog();
    }

    private void recordLog() {
        today.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss"));
        //  java.sql.Date sqlDate = java.sql.Date.valueOf(today);
        System.out.println("utilDate:" + today.toString());
        // System.out.println("sqlDate:" + sqlDate);
        String query = "INSERT INTO `testdb`.`log` (`id_log`, `id_operation`, `id_user`, `date`) \n"
                + " VALUES (NULL, '" + id_operation + "', '" + id_user + "', '" + today.format(DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")) + "');";
        conSql();
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
