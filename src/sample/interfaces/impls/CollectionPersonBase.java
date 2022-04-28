package sample.interfaces.impls;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.interfaces.PersonBase;
import sample.interfaces.impls.CollectionOperatingHall;
import sample.objects.Hall;
import sample.objects.Person;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

import static sample.interfaces.impls.CollectionOperatingHall.checkTipType;


public class CollectionPersonBase implements PersonBase {
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    private static ObservableList<Person> personList = FXCollections.observableArrayList();
    private static ArrayList<String> type = new ArrayList<>();
    private static ArrayList<String> group = new ArrayList<>();
    // private Person person;

    public CollectionPersonBase() {
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
        String dateSchedulePrivate;
        int group;
        int schedulePrivate;
        String query = "SELECT tik_id, tik_type, name, surname, patronymic, number, mail FROM sport";
        String query1 = "SELECT name FROM type_ticket";
        String query2 = "SELECT `id_group` FROM `group`";
        try {
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                int tik_id = rs.getInt(1);
                String tik_type = rs.getString(2);
                String name = rs.getString(3);
                String surname = rs.getString(4);
                String patronymic = rs.getString(5);
                String number = rs.getString(6);
                String mail = rs.getString(7);
                // int group = rs.getInt(6);
                //  int schedulePrivate = rs.getInt(7);
                String query3 = "SELECT `group` FROM `people_group` WHERE `id_sport` = " + tik_id;
                String query4 = "SELECT `will` FROM `schedule_private` WHERE `id_sport` = " + tik_id;
                group = getValue(query3);
                schedulePrivate = getValue(query4);
                System.out.printf("tik_id: %d, tik_type: %s, name: %s, surname: %s, patronymic: %s, group: %d, " +
                                "schedulePrivate: %d %n", tik_id, tik_type,
                        name, surname, patronymic, group, schedulePrivate);
                String query5 = "SELECT `date_schedule` FROM `testdb`.`schedule_private` WHERE `id_sport` = " + tik_id;
                dateSchedulePrivate = getDateSchedulePrivate(query5);
                System.out.println(dateSchedulePrivate);
                Person person = new Person(tik_id, tik_type, name, surname, patronymic, group, schedulePrivate, dateSchedulePrivate);
                person.setNumber(number);
                person.setMail(mail);
                personList.add(person);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        type = choice(query1);
        CollectionPersonBase.group = choice(query2);
        conSql();
        checkDate();
        for (Person aPersonList : personList) System.out.println(aPersonList);

    }

    private void checkingSubscription() {
        for (int i = 0; i < personList.size(); i++) {
            Person person = personList.get(i);
            String query = "SELECT status, date_salary FROM `sport_tik` where id_sport = " + person.getTik_id();
            try {
                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    boolean status = rs.getBoolean(1);
                    LocalDate date_transactions = rs.getObject(2, LocalDate.class);
                    if (status) personList.get(i).setCountSubscription(person.getCountSubscription() + 1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void checkDate() {
        LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));
        ArrayList<Integer> falseStatusList = new ArrayList<>();
        for (int i = 0; i < personList.size(); i++) {
            Person person = personList.get(i);
            String query = "SELECT status, date_salary, tik_type, date_end FROM `sport_tik` where id_sport = " + person.getTik_id();
            try {
                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    boolean status = rs.getBoolean(1);
                    LocalDate date_transactions = rs.getObject(2, LocalDate.class);
                    String tik_type = rs.getString(3);
                    LocalDate date_end = rs.getObject(4, LocalDate.class);
                    //date_transactions = date_transactions.plusDays(checkTipType(tik_type));
                    if (date_end.isBefore(dateNow)) {
                        System.out.println("Дата окончания:" + date_end +
                                " Сейчас:" + dateNow + " -> сейчас больше");
                        falseStatusList.add(person.getTik_id());
                    } else {
                        System.out.println("Дата окончания:" + date_end +
                                " Сейчас:" + dateNow + " -> сейчас меньше");
                        personList.get(i).setCountSubscription(person.getCountSubscription() + 1);
                    }
                    //  if (status) personList.get(i).setCountSubscription(person.getCountSubscription() + 1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < falseStatusList.size(); i++) {
            String query = "UPDATE `sport_tik` SET `status` = '0' WHERE `sport_tik`.`id_tik` = " + falseStatusList.get(i);
            try {
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println("IN CollectionPersonBase.checkDate");
            }

        }
    }

    public static void checkDate(Person person) {
        LocalDate dateNow = LocalDate.now(ZoneId.of("America/Montreal"));
        int max = 0;
        int count = 0;
        Boolean haveActiveSubscription = true;
        LocalDate dateEndFirstSubscription = null;
        ArrayList<Integer> idFalseSubscription = new ArrayList<>();
        String query = "SELECT status, date_salary, tik_type, date_end FROM `sport_tik` where id_sport = " + person.getTik_id();
        String query1 = "";
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                boolean status = rs.getBoolean(1);
                LocalDate date_transactions = rs.getObject(2, LocalDate.class);
                String tik_type = rs.getString(3);
                LocalDate date_end = rs.getObject(4, LocalDate.class);
                //date_transactions = date_transactions.plusDays(checkTipType(tik_type));
                if (date_end.isBefore(dateNow)) {
                    System.out.println("Дата окончания:" + date_end +
                            " Сейчас:" + dateNow + " -> сейчас больше");
                    idFalseSubscription.add(person.getTik_id());
                } else {
                    System.out.println("Дата окончания:" + date_end +
                            " Сейчас:" + dateNow + " -> сейчас меньше");
                    person.setCountSubscription(person.getCountSubscription() + 1);
                    if (!haveActiveSubscription) {
                        query1 = "TRUE";

                    } else {
                        haveActiveSubscription = false;
                        dateEndFirstSubscription = date_end;
                    }
                }
                //  if (status) personList.get(i).setCountSubscription(person.getCountSubscription() + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String qMax = "SELECT Max(`sport_tik`.`id_tik`) FROM `sport_tik`";

        if (!query1.equals(""))
            try {
                rs = stmt.executeQuery(qMax);
                while (rs.next()) {
                    max = rs.getInt(1);
                }

                query1 = "UPDATE `testdb`.`sport_tik` SET `date_end` =" +
                        " '" + dateEndFirstSubscription.plusDays(checkTipType(person.getTik_type())) +
                        "' WHERE `id_tik` = " + max;
                System.out.println(query1);
                stmt.executeUpdate(query1);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("IN CollectionPersonBase.checkDate");
            }

        for (Object anIdFalseSubscription : idFalseSubscription) {
            query = "UPDATE `sport_tik` SET `status` = '0' WHERE `sport_tik`.`id_tik` = " + anIdFalseSubscription;
            try {
                stmt.executeUpdate(query);
            } catch (SQLException e) {
                System.out.println("IN CollectionPersonBase.checkDate");
            }

        }
    }


    private int getValue(String query) {
        int gr = 0;
        ResultSet rs1;
        Statement stmt1;
        try {
            stmt1 = (Statement) con.createStatement();
            rs1 = stmt1.executeQuery(query);
            while (rs1.next()) {
                gr = rs1.getInt(1);
            }
            rs1.close();
            stmt1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gr;
    }

    private String getDateSchedulePrivate(String query3) {
        StringBuilder s = new StringBuilder("");
        ResultSet rs1;
        Statement stmt1;
        try {
            stmt1 = (Statement) con.createStatement();
            rs1 = stmt1.executeQuery(query3);
            while (rs1.next()) {
                assert false;

                s.append(rs1.getString(1));
                s.delete(s.length() - 2, s.length()).append(";");
            }
            rs1.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s.toString().trim();
    }

    private ArrayList<String> choice(String query) {
        ArrayList<String> arrayList = new ArrayList<>();

        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString(1);
                arrayList.add(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    @Override
    public void add(Person person) {
        conSql();
        String query = "INSERT INTO `testdb`.`sport` (`tik_id`,`tik_type`, `name`, `surname`, `patronymic`, `number`, `mail`) \n"
                + " VALUES (NULL,'" + person.getTik_type() + "', '" + person.getName() + "', '" +
                person.getSurname() + "', '" + person.getPatronymic() + "', '" + person.getNumber() +
                "', '" + person.getMail() + "');";
//        INSERT INTO `testdb`.`sport`
//        (`tik_type`, `name`, `surname`, `patronymic`) VALUES ('Безлимитный', 'Сергей', 'Петров', 'Олегович');
        // Это тоже можно оптимизировать
        System.out.println(query);
        //INSERT INTO `people_group` (`id`, `id_sport`, `group`, `id_trainer`) VALUES (NULL, '5', '0', '5');
        LocalDate todayLocalDate = LocalDate.now(ZoneId.of("America/Montreal"));
        try {
            stmt.executeUpdate(query);

            // INSERT INTO `testdb`.`sport_tik` (`id_sport`, `tik_type`, `date_salary`) VALUES ('6', 'fg', '45');

        } catch (SQLException e) {
            e.printStackTrace();
        }
        query = "SELECT * FROM `testdb`.`sport` WHERE tik_id=(SELECT MAX(tik_id) FROM `testdb`.`sport`);";
        // SELECT * FROM `testdb`.`sport` WHERE tik_id=(SELECT MAX(tik_id) FROM `testdb`.`sport`);
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) {
                int tik_id = rs.getInt(1);
                person.setTik_id(tik_id);
            }
            String query1 = "INSERT INTO `people_group` (`id`, `id_sport`, `group`, `id_trainer`) VALUES (NULL, '" + person.getTik_id() +
                    "', '" + person.getGroup() + "', '" + 0 + "')";
            System.out.println(query1);
            stmt.executeUpdate(query1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        personList.add(person);
        //Оптимизировать
        update(person);
    }

    @Override
    public void delete(Person person) {
        String query = "DELETE FROM `testdb`.`sport` WHERE `tik_id`='" + person.getTik_id() + "'";
        System.out.println(query);
        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        personList.remove(person);
    }

    @Override
    public void update(Person person) {
        conSql();
        int oldGroup = 0;
        String query = "UPDATE `testdb`.`sport` SET `name`='" + person.getName() + "', `surname`='" +
                person.getSurname() + "', `patronymic`='" + person.getPatronymic() + "' " +
                ",`number`='" + person.getNumber() + "'" + ",`mail`='" + person.getMail() + "' " +
                "WHERE `sport`.`tik_id` = " + person.getTik_id();
        String query4 = "UPDATE `people_group` SET `group`= '" + person.getGroup() + "' WHERE `people_group`.`id_sport` = " + person.getTik_id();
        //Сдесь можно оптимизировать
        //UPDATE `testdb`.`sport` SET `name`='Михаwил', `surname`='Акалаwван', `patronymic`='Серwгеевич', `group`='0' WHERE `tik_id`='1';
        ;
        String query2 = "SELECT `group` FROM `people_group` WHERE `id_sport` = " + person.getTik_id();
        try {
            rs = stmt.executeQuery(query2);
            while (rs.next()) oldGroup = rs.getInt(1);
            stmt.executeUpdate(query);
            updateGroup(person.getTik_id(), person.getGroup());
            String query1 = "UPDATE `testdb`.`group` SET `count_Clients` = '" + count(person.getGroup()) + "' WHERE `group`.`id_group` = " +
                    person.getGroup();
            String query3 = "UPDATE `testdb`.`group` SET `count_Clients` = '" + count(oldGroup) + "' WHERE `group`.`id_group` = " +
                    oldGroup;
            stmt.executeUpdate(query1);
            stmt.executeUpdate(query3);
            stmt.executeUpdate(query4);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void updateGroup(int tik_id, int group) {
        //  int trainer = 0;
        //String query = "SELECT `id_trainer` FROM `testdb`.`group` WHERE `id_group` = " + group;
        try {
//            rs = stmt.executeQuery(query);
//            while (rs.next()) trainer = rs.getInt(1);
            String query1 = "UPDATE `people_group` SET `group` = '" + group + "' " +
                    "WHERE `people_group`.`id_sport` = " + tik_id;
            stmt.executeUpdate(query1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int count(int group) {
        int k = 0;
        String query = "SELECT `id` FROM `people_group` WHERE `group` = " + group;
        //   String query1 = "SELECT `id_trainers` FROM `trainers` WHERE `group` = " + group;
        //  System.out.print("group: " + group);
        try {
            rs = stmt.executeQuery(query);
            while (rs.next()) k++;
            //    System.out.print(", in sport: " + k);
            //   rs = stmt.executeQuery(query1);
            //     while (rs.next()) k++;
            //     System.out.print(", in sport + trainers: " + k);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //  System.out.println(", all: " + k);
        return k;
    }

    public static Person getPerson(int idSport) {
        return personList.get(idSport);
    }

    public static ObservableList<Person> getPersonList() {
        return personList;
    }

    public static ArrayList<String> getType() {
        return type;
    }

    public static ArrayList<String> getGroup() {
        return group;
    }

    // public void setPerson(Person person) {
    //     this.person = person;
    //}
}
