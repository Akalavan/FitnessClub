package sample.objects;

public class User {
    private int id_user;
    private String name;
    private String surname;
    private String patronymic;
    private String password;

    public User(int id_user, String name, String surname, String patronymic, String password) {
        this.id_user = id_user;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.password = password;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return surname + " " + name + " " + patronymic;
    }
}
