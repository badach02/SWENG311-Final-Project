public class Receipt {
    private Customer customer;
    private Car car;
    private double price;

    public Receipt(Customer customer, Car car, double price) {
        this.customer = customer;
        this.car = car;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Receipt for " + customer.getName() + ": " + car.getModel() + " ($" + price + ")";
    }
}
