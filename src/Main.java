import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                List<Customer> customers = FileManager.loadCustomers();
                List<Car> cars = FileManager.loadCars(customers);
                List<Receipt> receipts = FileManager.loadReceipts();

                JFrame frame = new JFrame("Car Dealership");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 500);

                ReceiptManagerPanel receiptPanel = new ReceiptManagerPanel(receipts);
                CarManagerPanel carPanel = new CarManagerPanel(cars);
                CustomerManagerPanel customerPanel = new CustomerManagerPanel(customers, receipts);
                RentCarPanel rentPanel = new RentCarPanel(carPanel, customerPanel, receiptPanel);

                customerPanel.setRentPanel(rentPanel);
                carPanel.setRentPanel(rentPanel);

                JTabbedPane tabs = new JTabbedPane();
                tabs.addTab("Customers", customerPanel);
                tabs.addTab("Cars", carPanel);
                tabs.addTab("Rent Car", rentPanel);
                tabs.addTab("Receipts", receiptPanel);

                LandingPagePanel landingPage = new LandingPagePanel();
                CardLayout cardLayout = new CardLayout();
                JPanel mainPanel = new JPanel(cardLayout);

                mainPanel.add(landingPage, "landingPage");
                mainPanel.add(tabs, "mainPanel");
                cardLayout.show(mainPanel, "landingPage");
                landingPage.setStartButtonListener(e -> cardLayout.show(mainPanel, "mainPanel"));

                frame.add(mainPanel);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        try {
                            FileManager.saveCustomers(customerPanel.getCustomers());
                            FileManager.saveCars(cars);
                            FileManager.saveReceipts(receipts);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading data.");
            }
        });
    }
}


 