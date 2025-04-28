import java.io.*;
import java.util.*;

class FileManager {
    public static List<Customer> loadCustomers() throws IOException {
        List<Customer> customers = new ArrayList<>();
        File file = new File("data/customers.txt");
        if (!file.exists()) return customers;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                customers.add(new Customer(parts[0], Integer.parseInt(parts[1])));
            }
        }
        br.close();
        return customers;
    }

    public static void saveCustomers(List<Customer> customers) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/customers.txt"));
        for (Customer c : customers) {
            bw.write(c.getName() + "," + c.getId());
            bw.newLine();
        }
        bw.close();
    }

    public static void saveCars(List<Car> cars) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/cars.txt"));
        
        // Loop through each car and save its details
        for (Car car : cars) {
            // If the car is rented, get the renter's name; if not, leave it empty
            String renter = car.isRented() && car.getRenter() != null ? car.getRenter().getName() : "null";
            
            // Write car details to file
            bw.write(car.getMake() + "," + car.getModel() + "," + car.getYear() + "," + car.isRented() + "," + renter);
            bw.newLine();  // Add a new line for each car
        }
        
        // Close the BufferedWriter
        bw.close();
    }

    public static List<Car> loadCars(List<Customer> customers) throws IOException {
        List<Car> cars = new ArrayList<>();
        File file = new File("data/cars.txt");
        if (!file.exists()) return cars;
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 5);  // Allow model to contain commas
                
                if (parts.length == 5) {
                    String make = parts[0].trim();
                    String model = parts[1].trim();
                    int year = Integer.parseInt(parts[2].trim());
                    boolean isRented = Boolean.parseBoolean(parts[3].trim());
                    String renterName = parts[4].trim();
    
                    Car car = new Car(make, model, year);
    
                    if (isRented && renterName != null && !renterName.equalsIgnoreCase("null") && !renterName.isEmpty()) {
                        Customer renter = findCustomerByName(customers, renterName);
                        car.setRented(true);
                        car.setRenter(renter);
                    } else {
                        car.setRented(false);
                        car.setRenter(null);
                    }
    
                    cars.add(car);
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        }
    
        return cars;
    }
    
    
    private static Customer findCustomerByName(List<Customer> customers, String renterName) {
        for (Customer customer : customers) {
            if (customer.getName().equals(renterName)) {
                return customer;
            }
        }
        return null;  // Return null if no customer found with the given name
    }
    

    public static List<Receipt> loadReceipts() throws IOException {
        List<Receipt> receipts = new ArrayList<>();
        File file = new File("data/receipts.txt");
    
        if (!file.exists()) {
            System.out.println("No receipts file found.");
            return receipts;
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("::");
                if (parts.length == 3) {
                    // Parse customer
                    String[] customerParts = parts[0].split(",");
                    if (customerParts.length != 2) continue;
                    String customerName = customerParts[0].trim();
                    int customerId = Integer.parseInt(customerParts[1].trim());
                    Customer customer = new Customer(customerName, customerId);
    
                    // Parse car model (and maybe year if encoded later)
                    String carModel = parts[1].trim();
                    Car car = new Car("Unknown", carModel, 0); // Set year = 0 if unknown, mark as rented
    
                    // Parse price
                    double price = Double.parseDouble(parts[2].trim());
    
                    receipts.add(new Receipt(customer, car, price));
                } else {
                    System.out.println("Skipping malformed receipt line: " + line);
                }
            }
        }
    
        System.out.println("Loaded receipts: " + receipts.size());
        return receipts;
    }
    

    public static void saveReceipts(List<Receipt> receipts) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("data/receipts.txt"));

        for (Receipt receipt : receipts) {
            // Save customer ID, car model, and price to the file
            writer.write(receipt.getCustomer().getName() + "," +
                         receipt.getCustomer().getId() + "::" +
                         receipt.getCar().getModel() + "::" +
                         receipt.getPrice());
            writer.newLine();
        }

        writer.close();
    }
}
