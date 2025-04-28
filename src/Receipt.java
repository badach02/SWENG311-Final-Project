class Receipt {
    private Customer customer;
    private Car car;
    private double price;

    public Receipt(Customer customer, Car car, double price) {
        this.customer = customer;
        this.car = car;
        this.price = price;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Car getCar() {
        return car;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Customer: " + customer.getName() + ", Car: " + car.getModel() + ", Price: $" + price;
    }
}