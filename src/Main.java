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

                // Create panels first
                ReceiptManagerPanel receiptPanel = new ReceiptManagerPanel(receipts);
                CarManagerPanel carPanel = new CarManagerPanel(cars);
                CustomerManagerPanel customerPanel = new CustomerManagerPanel(customers, receipts);
                // Create RentCarPanel after CustomerManagerPanel to pass reference
                RentCarPanel rentPanel = new RentCarPanel(carPanel, customerPanel, receiptPanel); // Temporarily pass null for customerPanel

                // Now create CustomerManagerPanel and pass RentCarPanel reference

                
                // Update RentCarPanel with the correct customer list
                customerPanel.setRentPanel(rentPanel);

                JTabbedPane tabs = new JTabbedPane();
                tabs.addTab("Customers", customerPanel);
                tabs.addTab("Cars", carPanel);
                tabs.addTab("Rent Car", rentPanel);
                tabs.addTab("Receipts", receiptPanel);

                // Set up the landing page and main panel with CardLayout
                LandingPagePanel landingPage = new LandingPagePanel();
                CardLayout cardLayout = new CardLayout();
                JPanel mainPanel = new JPanel(cardLayout);

                mainPanel.add(landingPage, "landingPage");
                mainPanel.add(tabs, "mainPanel");

                // Show landing page first
                cardLayout.show(mainPanel, "landingPage");

                // Start button listener for switching to main panel
                landingPage.setStartButtonListener(e -> cardLayout.show(mainPanel, "mainPanel"));

                frame.add(mainPanel);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                // Handle window closing event to save data
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        try {
                            FileManager.saveCustomers(customers);
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


