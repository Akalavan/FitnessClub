package sample.interfaces;

import sample.objects.Person;

public interface OperationHall {
    void buy();

    void sale();

    void sale(Person person);

    void registration(int how, Person person);

    void deletePerson();
}
