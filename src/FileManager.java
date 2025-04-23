import java.io.*;
import java.util.*;

public class FileManager {
    public static List<Customer> loadCustomers() throws IOException {
        List<Customer> customers = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/customers.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            customers.add(new Customer(data[0], Integer.parseInt(data[1])));
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

    public static List<Car> loadCars() throws IOException {
        List<Car> cars = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("data/cars.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            cars.add(new Car(data[0], Integer.parseInt(data[1])));
        }
        br.close();
        return cars;
    }

    public static void saveCars(List<Car> cars) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/cars.txt"));
        for (Car c : cars) {
            bw.write(c.getModel() + "," + c.getYear());
            bw.newLine();
        }
        bw.close();
    }

    public static void saveReceipts(List<Receipt> receipts) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter("data/receipts.txt"));
        for (Receipt r : receipts) {
            bw.write(r.toString());
            bw.newLine();
        }
        bw.close();
    }
}
