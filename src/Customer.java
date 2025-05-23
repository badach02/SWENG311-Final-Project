import java.util.Objects;

public class Customer {
    private String name;
    private int id;

    public Customer(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }
}