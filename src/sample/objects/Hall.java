package sample.objects;

import javafx.beans.property.SimpleIntegerProperty;

public class Hall {
    private SimpleIntegerProperty id_hall = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty count_person = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty count_inventory = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty money = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty profit = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty loss = new SimpleIntegerProperty(0);

    public Hall(int id_hall) {
        this.id_hall = new SimpleIntegerProperty(id_hall);
    }

    public Hall(int id_hall, int count_person, int count_inventory, int money) {
        this.id_hall = new SimpleIntegerProperty(id_hall);
        this.count_person = new SimpleIntegerProperty(count_person);
        this.count_inventory = new SimpleIntegerProperty(count_inventory);
        this.money = new SimpleIntegerProperty(money);
    }

    public int getId_hall() {
        return id_hall.get();
    }

    public SimpleIntegerProperty id_hallProperty() {
        return id_hall;
    }

    public void setId_hall(int id_hall) {
        this.id_hall.set(id_hall);
    }

    public int getCount_person() {
        return count_person.get();
    }

    public SimpleIntegerProperty count_personProperty() {
        return count_person;
    }

    public void setCount_person(int count_person) {
        this.count_person.set(count_person);
    }

    public int getCount_inventory() {
        return count_inventory.get();
    }

    public SimpleIntegerProperty count_inventoryProperty() {
        return count_inventory;
    }

    public void setCount_inventory(int count_inventory) {
        this.count_inventory.set(count_inventory);
    }

    public int getMoney() {
        return money.get();
    }

    public SimpleIntegerProperty moneyProperty() {
        return money;
    }

    public void setMoney(int money) {
        this.money.set(money);
    }

    public int getProfit() {
        return profit.get();
    }

    public SimpleIntegerProperty profitProperty() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit.set(profit);
    }

    public int getLoss() {
        return loss.get();
    }

    public SimpleIntegerProperty lossProperty() {
        return loss;
    }

    public void setLoss(int loss) {
        this.loss.set(loss);
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id_hall=" + id_hall +
                ", count_person=" + count_person +
                ", count_inventory=" + count_inventory +
                ", money=" + money +
                ", profit=" + profit +
                ", loss=" + loss +
                '}';
    }
}
