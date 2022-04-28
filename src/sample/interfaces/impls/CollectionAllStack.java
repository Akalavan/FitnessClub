package sample.interfaces.impls;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.objects.Stock;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CollectionAllStack {

    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private static ObservableList<Stock> allStockList = FXCollections.observableArrayList();


    public CollectionAllStack() {
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

    public void date() {
        String query = "select * from stock";

        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt(1);
                LocalDate dateStart = rs.getObject(2, LocalDate.class);
                LocalDate dateEnd = rs.getObject(3, LocalDate.class);
                String condition = rs.getString(4);
                String comment = rs.getString(5);
                allStockList.add(new Stock(String.valueOf(dateStart), String.valueOf(dateEnd), condition, comment));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Stock> getAllStockList() {
        return allStockList;
    }
}
