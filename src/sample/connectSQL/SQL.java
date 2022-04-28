package sample.connectSQL;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL {

    private static SQL instance;

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection con;
    //private static Statement stmt;
    // private static ResultSet rs;

    private SQL() {
    }

    public static SQL getInstance() { // #3
        if (instance == null) {        //если объект еще не создан
            instance = new SQL();    //создать новый объект
            connect();
        }
        return instance;        // вернуть ранее созданный объект
    }

    private static void connect() {
        Statement stmt = null;
        try {
            con = (Connection) DriverManager.getConnection(
                    URL + "?user=" + USER + "&password=" + PASSWORD +
                            "&useUnicode=true&characterEncoding=utf8");
            stmt = (Statement) con.createStatement();
            setting(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Statement conSql(Statement stmt) {
        try {
            stmt = (Statement) con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stmt;
    }


    public static void close() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void setting(Statement stmt) {
        try {
            stmt.executeUpdate("SET GLOBAL connect_timeout=999999");
            stmt.executeUpdate("SET GLOBAL wait_timeout=999999");
            stmt.executeUpdate("SET GLOBAL interactive_timeout=999999");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet execute(String request, Statement stmt) {
        ResultSet rs;
        stmt = conSql(stmt);
        setting(stmt);
        try {
            return stmt.executeQuery(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void update(String request, Statement stmt) {
        stmt = conSql(stmt);
        setting(stmt);
        try {
            stmt.executeUpdate(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
