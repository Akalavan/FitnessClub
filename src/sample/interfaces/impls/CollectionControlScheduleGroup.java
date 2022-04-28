package sample.interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.connectSQL.SQL;
import sample.objects.Trainer;
import sample.objects.schedule.ScheduleGroup;
import sample.start.Main;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import java.time.LocalDate;

public class CollectionControlScheduleGroup {

    private int id_group = 0;

    private static ResultSet rs;

    private static ObservableList<ScheduleGroup> scheduleList = FXCollections.observableArrayList();

    public ObservableList<ScheduleGroup> getScheduleList() {
        return scheduleList;
    }

    public void date() {
        ResultSet rsT = null;
        String groupTrainer;
        String querySchedule = "SELECT * FROM `schedule_group` WHERE id_group = " + id_group;

//      SELECT trainers.name_trainer FROM `testdb`.`trainers`, `group` WHERE `group`.`id_group` = 1 and trainers.id_trainers = `group`.id_trainer
//      SELECT trainers.name_trainer, trainers.surname_trainer, trainers.patronymic_trainer FROM `testdb`.`trainers` INNER JOIN `group` ON trainers.id_trainers = `group`.id_trainer WHERE `group`.`id_group` = 1
        // System.out.println(query);
        rs = SQL.execute(querySchedule, null);

        try {
            assert rs != null;
            read();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void read() throws SQLException {
        ResultSet rsT;
        while (rs.next()) {
            int id = rs.getInt(1);
            String date = rs.getString(2);
            //  int id_group = rs.getInt(3);
            int day = rs.getInt(4);
            String queryTrainer = "SELECT id_trainer, count_Clients FROM `group` WHERE id_group = " + id_group;
            int id_trainer = 0, count_Clients = 0;
            rsT = SQL.execute(queryTrainer, null);
            assert rsT != null;
            while (rsT.next()) {
                id_trainer = rsT.getInt(1);
                count_Clients = rsT.getInt(2);
            }
            rsT.close();
            System.out.printf("id: %d, date: %s, id_group: %d, count_Clients: %d," +
                            " id_trainer: %d %n", id, date,
                    id_group, count_Clients, id_trainer);
            for (Trainer t :
                    CollectionControlTrainers.getTrainersList()) {
                if (id_trainer == t.getId_trainers()) {
                    scheduleList.add(new ScheduleGroup(id, date, t, id_group, count_Clients, day));
                }
            }
        }
        rs.close();
    }

    public void clear() {
        scheduleList = FXCollections.observableArrayList();
    }

    public void setId_group(int id_group) {
        this.id_group = id_group;
    }
}
