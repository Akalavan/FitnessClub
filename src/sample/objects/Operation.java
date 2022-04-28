package sample.objects;

public class Operation {
    int id_operation;
    String name;
    String comment;

    public Operation(int id_operation, String name, String comment) {
        this.id_operation = id_operation;
        this.name = name;
        this.comment = comment;
    }

    public int getId_operation() {
        return id_operation;
    }

    public void setId_operation(int id_operation) {
        this.id_operation = id_operation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
