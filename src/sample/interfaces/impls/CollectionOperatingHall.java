package sample.interfaces.impls;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.controllers.FirstTabController;
import sample.controllers.SecondTabController;
import sample.enums.CostTicket;
import sample.interfaces.OperationHall;
import sample.objects.Hall;
import sample.objects.MoneyProfit;
import sample.objects.Person;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static sample.interfaces.impls.CollectionPersonBase.checkDate;


public class CollectionOperatingHall implements OperationHall {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private ObservableList<Hall> hallList = FXCollections.observableArrayList();
    private static ObservableList<MoneyProfit> moneyProfits = FXCollections.observableArrayList();

    private Hall hall;
    private int money = 0;

    public CollectionOperatingHall() {
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

    public void getDateHall() {
        String query = "SELECT id_hall, hall_count_person, hall_count_inventory, hall_date FROM hall";
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id_hall = rs.getInt(1);
                int hall_count_person = rs.getInt(2);
                int hall_count_inventory = rs.getInt(3);
                System.out.printf("id_hall: %d, hall_count_person: %s, hall_count_inventory: %s %n", id_hall, hall_count_person,
                        hall_count_inventory);
                if (hall_count_person > 0) sale();
                hall = new Hall(id_hall, hall_count_person, hall_count_inventory, money);
                hallList.add(hall);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getDateMoneyProfit() {
        String query = "SELECT id_transactions, id_sport, money_as_much, date_transactions, id_hall FROM money_profit";
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int id_transactions = rs.getInt(1);
                int id_sport = rs.getInt(2);
                int money_as_much = rs.getInt(3);
                LocalDate date_transactions = rs.getObject(4, LocalDate.class);
                int id_hall = rs.getInt(5);
                System.out.printf("id_transactions: %d, money: %d, id_sport: %d, date: %s, id_hall: %d %n", id_transactions, money_as_much,
                        id_sport, date_transactions, id_hall);
                //   if (hall_count_person > 0) sale();
                //        hall = new Hall(id_hall, hall_count_person, hall_count_inventory, money);
//                hallList.add(hall);
                LocalDate todayLocalDate = LocalDate.now(ZoneId.of("America/Montreal"));
                LocalDate todayLocalDateEnd = todayLocalDate.plusDays(checkTipType(money_as_much));

                moneyProfits.add(new MoneyProfit(id_hall, money_as_much, id_sport, String.valueOf(date_transactions),
                        String.valueOf(todayLocalDateEnd)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void buy() {

    }

    @Override
    public void sale() {
        for (Person people : CollectionPersonBase.getPersonList()) {
            money += checkTipType(people);
        }
    }

    public static Integer checkTipType(Person person) {
        int money = 0;
        switch (person.getTik_type()) {
            case "Годовой":
                money += Integer.parseInt(CostTicket.ANNUAL.getClaim());
                break;
            case "Месяц":
                money += Integer.parseInt(CostTicket.MONTHLY.getClaim());
                break;
            case "Безлимитный":
                money += Integer.parseInt(CostTicket.UNLIMITED.getClaim());
                break;
            case "Неделя":
                money += Integer.parseInt(CostTicket.WEEK.getClaim());
                break;
        }
        return money;
    }

    public static Integer checkTipType(Integer money) {
        int day = 0;
        switch (money) {
            case 12000:
                day = 365;
                break;
            case 1300:
                day = 31;
                break;
            case 20000:
                day = 99999;
                break;
            case 555:
                day = 7;
                break;
            case 1:
                day = 365;
                break;
            case 2:
                day = 31;
                break;
            case 3:
                day = 99999;
                break;
            case 4:
                day = 7;
                break;
        }
        return day;
    }


    public static Integer checkTipType(String type) {
        System.out.println(type);
        int day = 0;
        switch (type) {
            case "Годовой":
                day = 365;
                break;
            case "Месяц":
                day = 31;
                break;
            case "Безлимитный":
                day = 99999;
                break;
            case "Неделя":
                day = 7;
                break;
            case "ГодовойM":
                day = Integer.parseInt(CostTicket.ANNUAL.getClaim());
                break;
            case "МесяцM":
                day = Integer.parseInt(CostTicket.MONTHLY.getClaim());
                break;
            case "БезлимитныйM":
                day = Integer.parseInt(CostTicket.UNLIMITED.getClaim());
                break;
            case "НеделяM":
                day = Integer.parseInt(CostTicket.WEEK.getClaim());
                break;
        }
        return day;
    }


    @Override
    public void sale(Person person) {
        System.out.println(person.getTik_type() + " CollectionOperatingHall.sale");
        registration(checkTipType(person), person);
    }

    @Override
    public void registration(int how, Person person) {
        conSql();
        System.out.println(person.getDiscount());
        Integer discountPrice = how;
        if (person.getDiscount() != 0) {
            System.out.println(person.getDiscount() / 100.0);
            discountPrice = (int) (how - Math.round(how * (person.getDiscount() / 100.0)));
        }
        hall.setMoney(hall.getMoney() + how);
        hall.setCount_person(hall.getCount_person() + 1);

        LocalDate todayLocalDate = LocalDate.now(ZoneId.of("America/Montreal"));
        LocalDate todayLocalDateEnd = todayLocalDate.plusDays(checkTipType(person.getTik_type()));

        System.out.println(person.getTik_type() + " CollectionOperatingHall.registration " + how);

        java.sql.Date sqlDate = java.sql.Date.valueOf(todayLocalDate);
        moneyProfits.add(new MoneyProfit(hall.getId_hall(), how, person.getTik_id(), String.valueOf(todayLocalDate),
                String.valueOf(todayLocalDateEnd)));
        String query = "UPDATE `testdb`.`hall` SET `hall_count_person`='" + hall.getCount_person() + "' WHERE `id_hall`='1';";
        //UPDATE `testdb`.`hall` SET `hall_count_person`='4' WHERE `id_hall`='1';

        String query1 = "INSERT INTO `testdb`.`money_profit` (`id_sport`, `money_as_much`, `date_transactions`, `id_hall`, `discount_price`) VALUES " +
                "('" + person.getTik_id() + "','" + how + "', '" + todayLocalDate + "', '" + hall.getId_hall() + "', '" + discountPrice + "');";
        //INSERT INTO `testdb`.`money_profit` (`money_as_much`, `date_transactions`, `id_hall`) VALUES ('23', '13.03.2018', '1');
        System.out.println(query);
        System.out.println(query1);
        try {
            stmt.executeUpdate(query);
            stmt.executeUpdate(query1);
            query = "INSERT INTO `testdb`.`sport_tik` (`id_tik`, `id_sport`, `tik_type`, `date_salary`, `date_end`, `status`) \n"
                    + " VALUES (NULL, '" + person.getTik_id() + "', '" + person.getTik_type() + "', '" +
                    todayLocalDate + "', '" + todayLocalDateEnd + "', '" + 1 + "');";
            System.out.println(query + " CollectionOperatingHall.registration");
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        checkDate(person);
    }

    @Override
    public void deletePerson() {
        hall.setCount_person(hall.getCount_person() - 1);
        String query = "UPDATE `testdb`.`hall` SET `hall_count_person`='" + hall.getCount_person() + "' WHERE `id_hall`='1';";
        //    UPDATE `testdb`.`hall` SET `hall_count_person`='4' WHERE `id_hall`='1';
        System.out.println(query);
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getMoney(Person person) {
        int money = 0;
        for (MoneyProfit moneyProfit :
                moneyProfits) {
            if (moneyProfit.getId_sport() == person.getTik_id()) money += moneyProfit.getMoney_as_much();
        }
        return money;
    }

    public void setHall() {
        this.hall = SecondTabController.getHallBaseSecondTable().getHall();
    }


    public ObservableList<Hall> getHallList() {
        return hallList;
    }

    public ObservableList<MoneyProfit> getMoneyProfits() {
        return moneyProfits;
    }

    public void setMoneyProfits(ObservableList<MoneyProfit> moneyProfits) {
        this.moneyProfits = moneyProfits;
    }

    public Hall getHall() {
        return hall;
    }
}

