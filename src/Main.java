import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    List<Customer> customers = FileManager.loadCustomers();
                    List<Car> cars = FileManager.loadCars();
                    List<Receipt> receipts = new ArrayList<>();

                    JFrame frame = new JFrame("Car Dealership");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(600, 400);

                    CustomerManagerPanel customerPanel = new CustomerManagerPanel(customers);
                    CarManagerPanel carPanel = new CarManagerPanel(cars);
                    ReceiptManagerPanel receiptPanel = new ReceiptManagerPanel(customerPanel, carPanel, receipts);

                    JTabbedPane tabs = new JTabbedPane();
                    tabs.addTab("Customers", customerPanel);
                    tabs.addTab("Cars", carPanel);
                    tabs.addTab("Receipts", receiptPanel);

                    frame.add(tabs);
                    frame.setVisible(true);

                    frame.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                            try {
                                FileManager.saveCustomers(customerPanel.getCustomers());
                                FileManager.saveCars(carPanel.getCars());
                                FileManager.saveReceipts(receiptPanel.getReceipts());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Could not load data.");
                }
            }
        });
    }
}