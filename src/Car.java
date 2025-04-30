
public class Car {
    private String make;
    private String model;
    private int year;
    private boolean isRented;
    private Customer renter;

    public Car(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.isRented = false;
        this.renter = null;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public Customer getRenter() {
        return renter;
    }

    public void setRenter(Customer renter) {
        this.renter = renter;
    }

    @Override
    public String toString() {
        if(renter != null){
            return year + " " + make + " " + model + " - " + renter.getName();
        }
        else{
            return year + " " + make + " " + model;
        }
    }
}
