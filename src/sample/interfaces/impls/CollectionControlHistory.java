package sample.interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.connectSQL.SQL;
import sample.objects.history;
import sample.objects.schedule.ScheduleGroup;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CollectionControlHistory {

    private static ResultSet rs;

    private int id_user = 0;

    private static ObservableList<history> historyList = FXCollections.observableArrayList();

    public static ObservableList<history> getHistoryList() {
        return historyList;
    }

    public void date() {
        ResultSet rsT = null;
        String groupTrainer;
        String querySchedule = "SELECT tik_type, date_salary FROM `sport_tik` WHERE id_sport = " + id_user;

//      SELECT trainers.name_trainer FROM `testdb`.`trainers`, `group` WHERE `group`.`id_group` = 1 and trainers.id_trainers = `group`.id_trainer
//      SELECT trainers.name_trainer, trainers.surname_trainer, trainers.patronymic_trainer FROM `testdb`.`trainers` INNER JOIN `group` ON trainers.id_trainers = `group`.id_trainer WHERE `group`.`id_group` = 1
        // System.out.println(query);
        rs = SQL.execute(querySchedule, null);

        try {
            while (rs.next()) {
                String type = rs.getString(1);
                String date = rs.getString(2);
                int cost = CollectionOperatingHall.checkTipType(type + "M");
                historyList.add(new history(type, date, cost));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void clear() {
        historyList = FXCollections.observableArrayList();
    }
}
