package sample.interfaces.impls;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.connectSQL.SQL;
import sample.objects.Group;
import sample.objects.Trainer;
import sample.objects.schedule.ScheduleGroup;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CollectionControlGroup {

    private static ObservableList<Group> groupsList = FXCollections.observableArrayList();

    public static ObservableList<Group> getGroupsList() {
        return groupsList;
    }

    public void date() {
        ResultSet rs, rsT;
        String queryGroup = "SELECT * FROM `group` WHERE id_group > 0";

//      SELECT trainers.name_trainer FROM `testdb`.`trainers`, `group` WHERE `group`.`id_group` = 1 and trainers.id_trainers = `group`.id_trainer
//      SELECT trainers.name_trainer, trainers.surname_trainer, trainers.patronymic_trainer FROM `testdb`.`trainers` INNER JOIN `group` ON trainers.id_trainers = `group`.id_trainer WHERE `group`.`id_group` = 1
        // System.out.println(query);
        rs = SQL.execute(queryGroup, null);

        try {
            assert rs != null;
            while (rs.next()) {
                int id = rs.getInt(1);
                int count_Clients = rs.getInt(2);
                int id_trainer = rs.getInt(3);
                String name = rs.getString(4);
                System.out.printf("id: %d, count_Clients: %d, id_trainer: %d, name: %s %n", id, count_Clients,
                        id_trainer, name);
                for (Trainer t :
                        CollectionControlTrainers.getTrainersList()) {
                    System.out.println(t.getName_trainer() + " " + t.getId_trainers());
                    System.out.println(id_trainer);
                    if (id_trainer == t.getId_trainers()) {
                        groupsList.add(new Group(id, name, t, count_Clients));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
