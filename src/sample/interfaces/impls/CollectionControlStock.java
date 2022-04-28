package sample.interfaces.impls;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import sample.interfaces.ControlStack;
import sample.objects.Person;
import sample.objects.Stock;
import sample.objects.StockPerson;
import sample.objects.StockStatus;

import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static sample.interfaces.impls.CollectionAllStack.getAllStockList;
import static sample.interfaces.impls.CollectionOperatingHall.checkTipType;
import static sample.interfaces.impls.CollectionOperatingHall.getMoney;
import static sample.interfaces.impls.CollectionPersonBase.getPerson;
import static sample.interfaces.impls.CollectionPersonBase.getPersonList;

public class CollectionControlStock implements ControlStack {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private static HashMap<Address, String> emailAddresses = new HashMap<>();

    private HashMap<Integer, Integer> stockPeople;

    private static ObservableList<StockPerson> stockPersonList = FXCollections.observableArrayList();
    private static ObservableList<Person> stockObservableList = FXCollections.observableArrayList();
    // private ArrayList<StockPerson> stockPerson = new ArrayList<>();
    private ArrayList<Stock> stockList;

    private String[] name, surname, patronymic, number, mail;
    private Boolean[] status;

    public CollectionControlStock() {

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


    public static ObservableList<StockPerson> getStockList() {
        return stockPersonList;
    }

    public static ObservableList<Person> getStockObservableList() {
        return stockObservableList;
    }

    public void date() {
        String query = "SELECT id_sport FROM money_profit";
        String queryCheck = "select id_sport from `active_stock` limit 1";
        conSql();

        try {

            rs = stmt.executeQuery(queryCheck);
            if (rs.next()) {
                ObservableMap<Person, LocalDate> observabl = FXCollections.observableHashMap();
                queryCheck = "select * from `active_stock`";
                rs = stmt.executeQuery(queryCheck);
                while (rs.next()) {
                    int idSport = rs.getInt(2);
                    int idStock = rs.getInt(3);
                    LocalDate dateBind = rs.getObject(4, LocalDate.class);
                    int statusEmail = rs.getInt(5);
                    int statusActive = rs.getInt(6);
                    for (Stock stock : getAllStockList()) {
                        // System.out.println(stock.getIdPersonDate());
                        if (stock.getId_stock() == idStock) {
                            Person person = getPerson(idSport - 1);
                            fillStock1(observabl, dateBind, person, stock);
                            if (!stockObservableList.contains(person))
                                stockObservableList.add(person);
                            putEmail(stock, person);
                        }
                    }
                }
            } else {
                ObservableMap<Person, LocalDate> observabl = FXCollections.observableHashMap();
                LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));
                for (Person person :
                        getPersonList()) {
                    for (Stock stock :
                            getAllStockList()) {
                        String[] condition = stock.getCondition().split(";");
                        if (checkConditions(person, getMoney(person), condition)) {
                            fillStock1(observabl, dateNow, person, stock);
                            if (!stockObservableList.contains(person))
                                stockObservableList.add(person);
                            putEmail(stock, person);
                            addInActiveStock(person, stock);
                        }
                    }

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        updateStock();
//
//        // Считаем количество абонементов у клиента
//        countSubClients(query);
//
//        // Собираем информацию о клиентах
//        collectionInfAboutClients();
//        // Добавляем активные акции
//        addActiveStock();
//        // Проверка подходит ли клиент под акцию
//        checkClientForStock();
//        // Заполняем таблицу "active_stock"
//        //fillActiveStock();
//        //Заполняем адрес и сообщения клиентов
//        fillEmailPerson();
//
//        String id;
//        String count = "";
//        for (Map.Entry entry : emailAddresses.entrySet()) {
//            id = ((InternetAddress) entry.getKey()).getAddress();
//            count = (String) entry.getValue();
//            System.out.println("Key: " + id + " Value: " + count);
//        }
        System.out.println(getAllStockList().size());
        for (Stock stock :
                getAllStockList()) {
            //stock.setIdPersonDate(observabl);
            for (Map.Entry entry :
                    stock.getIdPersonDate().entrySet()) {
                System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
                Person person = (Person) entry.getKey();

                for (Map.Entry entry1 : person.getStocks().entrySet()) {
                    System.out.println("Key: " + entry1.getKey() + " Value: " + entry1.getValue());
                }
            }

        }
    }

    private void putEmail(Stock stock, Person person) {
        try {
            if (!person.getMail().equals(""))
                emailAddresses.put(new InternetAddress(person.getMail()), stock.getComment());
        } catch (AddressException e) {
            e.printStackTrace();
        }
    }

    private void updateStock() {
        ObservableMap<Person, LocalDate> observabl = FXCollections.observableHashMap();
        LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));
        for (Person person :
                getPersonList()) {
            for (Stock stock :
                    getAllStockList()) {
                String[] condition = stock.getCondition().split(";");
                if (checkConditions(person, getMoney(person), condition)) {

                    if (!stockObservableList.contains(person)) {
                        stockObservableList.add(person);
                        putEmail(stock, person);
                        fillStock1(observabl, dateNow, person, stock);
                        addInActiveStock(person, stock);
                    } else {
                        //System.out.println("id: " + person.getTik_id());
                        for (Map.Entry entry :
                                person.getStocks().entrySet()) {
                            //System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue() + " upS");
                            if (person.getStocks().containsKey(stock)) {
                                // Stock stock1 = getAllStockList().get(Integer.parseInt(entry.getKey().toString()) - 1);
                                StockStatus stockStatus = (StockStatus) entry.getValue();
                                String[] condition1 = stock.getCondition().split(";");

                                if (dateIsBefore(LocalDate.parse(stockStatus.getDateBird()), checkTipType(Integer.parseInt(condition1[2])))) {
                                    fillStock1(observabl, dateNow, person, stock);
                                    if (!stockObservableList.contains(person)) {
                                        stockObservableList.add(person);
                                        putEmail(stock, person);
                                    }
                                    addInActiveStock(person, stock);
                                }
                            } else {
                                fillStock1(observabl, dateNow, person, stock);
                                if (!stockObservableList.contains(person)) {
                                    stockObservableList.add(person);
                                    putEmail(stock, person);
                                }
                                addInActiveStock(person, stock);
                            }
                        }
                    }
                }
            }
        }


//        ObservableList<Person> tmpUpdatePerson = FXCollections.observableArrayList();
//        for (Person person:
//                getPersonList()) {
//            for (Stock stock:
//                    getAllStockList()) {
//                String[] condition = stock.getCondition().split(";");
//                if (checkConditions(person, getMoney(person), condition)) {
//
//                    if (!stockObservableList.contains(person)) {
//                        stockObservableList.add(person);
//                    } else {
//
//                   //     if (dateIsBefore(stock.getIdPersonDate().get(person), checkTipType(condition[2]))) {
//                            for (Person person1:
//                                    stockObservableList) {
//
//                                    if (person1.equals(person)) {
//
//                                        System.out.println(stock.getIdPersonDate().get(person));
//                                       // if (dateIsBefore(stock.getIdPersonDate().get(person), checkTipType(condition[2]))) {
//                                        if (person.getStocks().containsKey(stock))
//                                            person.getStocks().get(stock).setCountStock();
//                                        else person.getStocks().put(stock, new StockStatus());
//                                    //}
//                                }
//                            }
//                            tmpUpdatePerson.add(person);
//                    //    }
//                    }
//
//
//                }
//            }
//
//        }
        // stockObservableList.addAll(tmpUpdatePerson);

    }

    private void addInActiveStock(Person person, Stock stock) {
        LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));
        Iterator iterator = person.getStocks().entrySet().iterator();
        if (iterator.hasNext()) {
            String[] s = iterator.next().toString().split("=");
            System.out.println(stock.getId_stock() == Integer.parseInt(s[0]));
            if (stock.getId_stock() == Integer.parseInt(s[0])) {
                String queryAddNewPerson = "INSERT INTO `active_stock` (`id_active`, `id_sport`, `id_stock`, `date_bind`) VALUES (NULL, '" +
                        person.getTik_id() + "', '" + stock.getId_stock() + "', '" + dateNow + "');";
                System.out.println(queryAddNewPerson);
                try {
                    stmt.executeUpdate(queryAddNewPerson);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addInActiveStock(Person person) {
        LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));

        for (Map.Entry entry :
                person.getStocks().entrySet()) {
            String queryAddNewPerson = "INSERT INTO `active_stock` (`id_active`, `id_sport`, `id_stock`, `date_bind`) VALUES (NULL, '" +
                    person.getTik_id() + "', '" + Integer.parseInt(entry.getKey().toString()) + "', '" + dateNow + "');";
            System.out.println(queryAddNewPerson);
            try {
                stmt.executeUpdate(queryAddNewPerson);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void addInActiveStock() {
        LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));
        for (Stock stock :
                getAllStockList()) {
            //stock.setIdPersonDate(observabl);
            for (Map.Entry entry :
                    stock.getIdPersonDate().entrySet()) {
                System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
                Person person = (Person) entry.getKey();

                for (Map.Entry entry1 : person.getStocks().entrySet()) {
                    System.out.println("Key: " + entry1.getKey() + " Value: " + entry1.getValue());
                    String queryAddNewPerson = "INSERT INTO `active_stock` (`id_active`, `id_sport`, `id_stock`, `date_bind`) VALUES (NULL, '" +
                            person.getTik_id() + "', '" + stock.getId_stock() + "', '" + dateNow + "');";
                    System.out.println(queryAddNewPerson);
                    try {
                        stmt.executeUpdate(queryAddNewPerson);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }


//    private void fillStock(ObservableMap<Person, LocalDate> observabl, LocalDate dateNow, Person person, Stock stock) {
//        StockStatus stockStatus = new StockStatus();
//        if (!person.getStocks().isEmpty()){
//            if (!person.getStocks().containsKey(stock)) {
//                person.getStocks().put(stock, 1);
//            } else {
//                person.getStocks().put(stock, person.getStocks().get(stock) + 1);
//            }
//        } else {
//            person.getStocks().put(stock, 1);
//        }
//        observabl.put(person, dateNow);
//        //  stock.getIdPersonDate().put(person, dateBind);
//        stock.setIdPersonDate(observabl);
//    }

    private void fillStock1(ObservableMap<Person, LocalDate> observabl, LocalDate dateNow, Person person, Stock stock) {
        if (!person.getStocks().isEmpty()) {
            if (!person.getStocks().containsKey(stock)) {
                StockStatus stockStatus = new StockStatus();
                stockStatus.setDateBird(String.valueOf(dateNow));
                person.getStocks().put(stock, stockStatus);
            } else {
                person.getStocks().get(stock).setCountStock(String.valueOf(dateNow));
            }
        } else {
            StockStatus stockStatus = new StockStatus();
            stockStatus.setDateBird(String.valueOf(dateNow));
            person.getStocks().put(stock, stockStatus);
        }
        observabl.put(person, dateNow.plusDays(person.getTik_id()));
        //  stock.getIdPersonDate().put(person, dateBind);
        stock.setIdPersonDate(observabl);
    }

    //Заполняем адрес и сообщения клиентов
    private void fillEmailPerson() {
        emailAddresses = new HashMap<>();
        for (Stock stock :
                stockList) {
            for (StockPerson stockPerson :
                    stockPersonList) {
                if (stockPerson.getStock() == stock.getId_stock()) {
                    String email = stockPerson.getEmail();
                    if (!email.equals("")) try {
                        emailAddresses.put(new InternetAddress(email), stock.getComment());
                    } catch (AddressException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // Заполняем таблицу "active_stock"
    private void fillActiveStock(ObservableList<StockPerson> tmpStockPerson) {
        HashMap<Integer, Integer> sportStock = new HashMap<>();
        HashMap<Integer, Integer> newSportStock = new HashMap<>();
        HashMap<Integer, LocalDate> dateIdPerson = new HashMap<>();
        LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));
        String queryCheck = "select id_sport, id_stock from `active_stock` limit 1";
        try {
            rs = stmt.executeQuery(queryCheck);
            if (rs.next()) {
                String querySelect = "select * from `active_stock`";
                rs = stmt.executeQuery(querySelect);
                while (rs.next()) {
                    sportStock.put(rs.getInt(2), rs.getInt(3));
                    dateIdPerson.put(rs.getInt(2), rs.getObject(4, LocalDate.class));
                }
                int i = 0;
                for (StockPerson stockPerson : tmpStockPerson) {
                    for (Map.Entry entry : sportStock.entrySet()) {
                        if ((int) entry.getKey() == stockPerson.getTik_id()) {
                            if ((int) entry.getValue() != stockPerson.getStock()) {
                                if (fullCheckPerson(stockPerson.getTik_id(), stockPerson.getStock()))
                                    newSportStock.put(stockPerson.getTik_id(), stockPerson.getStock());
                            } else {
                                String[] condition = getCondition(stockPerson);
                                if (condition.length == 3) {
                                    if (dateIsBefore(dateIdPerson.get(entry.getKey()), checkTipType(condition[2]))) {
                                        newSportStock.put(stockPerson.getTik_id(), stockPerson.getStock());
                                    }
                                }
                            }
                        } else if (!sportStock.containsKey(stockPerson.getTik_id()))
                            newSportStock.put(stockPerson.getTik_id(), stockPerson.getStock());
                    }
                }
            } else {
                System.out.println("else");
                for (StockPerson stockPerson : tmpStockPerson) {
                    String queryActiveStock = "INSERT INTO `active_stock` (`id_active`, `id_sport`, `id_stock`) VALUES (NULL, '" +
                            stockPerson.getTik_id() + "', '" + stockPerson.getStock() + "');";
                    stmt.executeUpdate(queryActiveStock);
                    stockPersonList.add(stockPerson);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (!newSportStock.isEmpty()) {
            System.out.println("!newSportStock.isEmpty()");
            try {
                for (Map.Entry entry : newSportStock.entrySet()) {
                    String queryAddNewPerson = "INSERT INTO `active_stock` (`id_active`, `id_sport`, `id_stock`) VALUES (NULL, '" +
                            entry.getKey() + "', '" + entry.getValue() + "');";
                    System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
                    stmt.executeUpdate(queryAddNewPerson);
                    for (StockPerson stockPerson : tmpStockPerson) {
                        if (stockPerson.getTik_id() == (int) entry.getKey() && stockPerson.getStock() == (int) entry.getValue()) {
                            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
                            stockPersonList.add(stockPerson);
                            break;
                        }
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stockPersonList.isEmpty()) {
            System.out.println("stockPersonList.isEmpty()");
            ArrayList<Integer> idSport = new ArrayList<>();
            ArrayList<Integer> idStock = new ArrayList<>();
            String querySelectActiveStock = "SELECT id_sport, id_stock FROM `active_stock`";
            try {
                rs = stmt.executeQuery(querySelectActiveStock);
                while (rs.next()) {
                    idSport.add(rs.getInt(1));
                    idStock.add(rs.getInt(2));
                }
                int i = 0;
                for (Integer entry : idSport) {
                    String querySelectPerson = "SELECT tik_id, name, surname, patronymic, number, mail, status FROM testdb.sport WHERE " +
                            "tik_id = " + entry;
                    System.out.println(querySelectPerson);
                    rs = stmt.executeQuery(querySelectPerson);
                    while (rs.next())
                        stockPersonList.add(new StockPerson(rs.getInt(1), idStock.get(i), 0,
                                rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                                rs.getString(6), rs.getBoolean(7)));
                    i++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String[] getCondition(StockPerson stockPerson) throws SQLException {
        String cond = "";
        try {
            String queryStock = "SELECT `condition` FROM `testdb`.`stock` WHERE `id_stock` = " + stockPerson.getStock();
            rs = stmt.executeQuery(queryStock);
            if (rs.next()) cond = rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cond.split(";");
    }

    private boolean dateIsBefore(LocalDate date) {
        LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));
        return date.isBefore(dateNow);
    }

    private boolean dateIsBefore(LocalDate date, int plus) {
        LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));
        return date.plusDays(plus).isBefore(dateNow);
    }

    private boolean fullCheckPerson(int idSport, int idStock) {
        LocalDate date = null;
        String condition = null;
        try {
            String queryAllActiveStock = "SELECT id_sport, id_stock, date_bind FROM `active_stock` WHERE id_sport = " + idSport
                    + " and id_stock = " + idStock;
            rs = stmt.executeQuery(queryAllActiveStock);
            while (rs.next()) {
                date = rs.getObject(3, LocalDate.class);
            }
            if (date != null) {
                String queryStock = "SELECT `condition` FROM `testdb`.`stock` WHERE `id_stock` = " + idStock;
                rs = stmt.executeQuery(queryStock);
                if (rs.next())
                    condition = rs.getString(1);
                return condition.split(";").length == 3 && dateIsBefore(date, checkTipType(condition.split(";")[2]));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Проверка подходит ли клиент под акцию
    private void checkClientForStock() {
        if (!stockList.isEmpty()) {
            ObservableList<StockPerson> tmpStockPerson = FXCollections.observableArrayList();
            for (int i = 0; i < stockPeople.size(); i++) {
                Person person = getPersonList().get(i);
                String queryMoney = "SELECT money_as_much FROM `money_profit` WHERE id_sport = " + person.getTik_id();
                int money = 0;
                try {
                    rs = stmt.executeQuery(queryMoney);
                    while (rs.next()) {
                        money += rs.getInt(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < stockList.size(); j++) {
                    Stock stock = stockList.get(j);
                    String[] condition = stock.getCondition().split(";");

                    if (checkConditions(person, money, condition)) {
                        tmpStockPerson.add(new StockPerson((i + 1), stock.getId_stock(), person.getCountSubscription(),
                                name[i], surname[i], patronymic[i], number[i], mail[i], status[i]));
                        System.out.println(name[i] + " " + surname[i] + " " + patronymic[i]);
                    }
                }

            }
            fillActiveStock(tmpStockPerson);
        }
    }

    private boolean checkConditions(Person person, int money, String[] condition) {
        // Считаем количество абонементов одного типа
        HashMap<Integer, Integer> countSimilarTypeTik = getCountTypeTik(person);
        if (condition.length == 2) {
            if (person.getCountSubscription() >= Integer.parseInt(condition[0]) &&
                    money >= Integer.parseInt(condition[1])) {
                //System.out.println(stockPersonList.size() + " stockPersonList");
                return true;
            }
        } else if (countSimilarTypeTik.get(Integer.parseInt(condition[2])) >= Integer.parseInt(condition[0]) &&
                money >= Integer.parseInt(condition[1])) {
            //System.out.println(stockPersonList.size() + " stockPersonList");
            return true;
        }
        return false;
    }

    // Считаем количество абонементов одного типа
    private HashMap<Integer, Integer> getCountTypeTik(Person person) {
        String queryType = "SELECT tik_type FROM `sport_tik` WHERE id_sport = " + person.getTik_id();
        HashMap<Integer, Integer> countSimilarTypeTik = new HashMap<>();
        countSimilarTypeTik.put(1, 0);
        countSimilarTypeTik.put(2, 0);
        countSimilarTypeTik.put(3, 0);
        countSimilarTypeTik.put(4, 0);
        try {
            rs = stmt.executeQuery(queryType);
            while (rs.next()) {
                String type = rs.getString(1);
                // Определяем тип абонемента
                int typeInt = getTypeTikInt(type);
                if (!countSimilarTypeTik.isEmpty()) {
                    countSimilarTypeTik.put(typeInt, countSimilarTypeTik.get(typeInt) + 1);
                } else {
                    countSimilarTypeTik.put(typeInt, 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countSimilarTypeTik;
    }

    // Определяем тип абонемента
    public static int getTypeTikInt(String type) {
        int typeInt = 0;
        switch (checkTipType(type)) {
            case 365:
                typeInt = 1;
                break;
            case 31:
                typeInt = 2;
                break;
            case 99999:
                typeInt = 3;
                break;
            case 7:
                typeInt = 4;
                break;
        }
        return typeInt;
    }

    // Добавляем активные акции
    private void addActiveStock() {
        stockList = new ArrayList<>();
        String query1;
        LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));
        query1 = "SELECT id_stock, date_start, date_end, `condition`, comment FROM stock";
        try {
            rs = stmt.executeQuery(query1);
            while (rs.next()) {
                int id = rs.getInt(1);
                LocalDate dateStart = rs.getObject(2, LocalDate.class);
                LocalDate dateEnd = rs.getObject(3, LocalDate.class);
                String condition = rs.getString(4);
                String comment = rs.getString(5);
                if (!dateEnd.isBefore(dateNow)) {
                    stockList.add(new Stock(String.valueOf(dateStart), String.valueOf(dateEnd), condition, comment));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Собираем информацию о клиентах
    private void collectionInfAboutClients() {
        String query1;
        for (int i = 0; i < stockPeople.size(); i++) {
            try {
                // System.out.println(stockPeople.get(0) + "======================");
                query1 = "SELECT name, surname, patronymic, number, mail, status FROM sport WHERE tik_id = " + (i + 1);

                rs = stmt.executeQuery(query1);
                while (rs.next()) {

                    name[i] = rs.getString(1);
                    surname[i] = rs.getString(2);
                    patronymic[i] = rs.getString(3);
                    number[i] = rs.getString(4);
                    mail[i] = rs.getString(5);
                    status[i] = rs.getBoolean(6);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // Считаем количество абонементов у клиента
    private void countSubClients(String query) {
        stockPeople = new HashMap<>();
        try {
            rs = stmt.executeQuery(query);
            int count = 0;
            while (rs.next()) {
                int id_sport = rs.getInt(1);
                if (!stockPeople.isEmpty()) {
                    if (!stockPeople.containsKey(id_sport)) {
                        stockPeople.put(id_sport, 1);
                    } else stockPeople.put(id_sport, stockPeople.get(id_sport) + 1);
                } else {
                    stockPeople.put(id_sport, 1);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        name = new String[stockPeople.size()];
        surname = new String[stockPeople.size()];
        patronymic = new String[stockPeople.size()];
        number = new String[stockPeople.size()];
        mail = new String[stockPeople.size()];
        status = new Boolean[stockPeople.size()];
    }

    public static HashMap<Address, String> getEmailAddresses() {
        return emailAddresses;
    }


}

//        int id, count, i = 0;
//        for (Map.Entry entry : stockPeople.entrySet()) {
//            id = (int) entry.getKey();
//            count = (int) entry.getValue();
//            System.out.println("Key: " + id + " Value: "
//                    + count);
//            stockPersonList.add(new StockPerson(id, 1, count, name[i], surname[i], patronymic[i], number[i], mail[i], status[i]));
//            try {
//                if (i < 6)
//                    addresses[i] = new InternetAddress(mail[i]);
//            } catch (AddressException e) {
//                e.printStackTrace();
//            }
//            i++;
//        }