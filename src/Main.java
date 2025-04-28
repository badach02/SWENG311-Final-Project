import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Load customers first
                List<Customer> customers = FileManager.loadCustomers();

                // Load cars based on the customers
                List<Car> cars = FileManager.loadCars(customers);

                // Load receipts with the cars and customers lists
                List<Receipt> receipts = FileManager.loadReceipts();

                // Create the JFrame and panels
                JFrame frame = new JFrame("Car Dealership");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 500);

                // Panels for managing the different sections of the UI
                ReceiptManagerPanel receiptPanel = new ReceiptManagerPanel(receipts);
                CustomerManagerPanel customerPanel = new CustomerManagerPanel(customers, receipts);
                CarManagerPanel carPanel = new CarManagerPanel(cars);
                RentCarPanel rentPanel = new RentCarPanel(carPanel, customerPanel, receiptPanel);

                // Update the rent car panel's list
                rentPanel.updateLists();

                // Set up the tabs for main content (Customers, Cars, Rent Car, Receipts)
                JTabbedPane tabs = new JTabbedPane();
                tabs.addTab("Customers", customerPanel);
                tabs.addTab("Cars", carPanel);
                tabs.addTab("Rent Car", rentPanel);
                tabs.addTab("Receipts", receiptPanel);

                // Create the landing page panel
                LandingPagePanel landingPage = new LandingPagePanel();

                // Set up the main panel using CardLayout
                CardLayout cardLayout = new CardLayout();
                JPanel mainPanel = new JPanel(cardLayout);

                // Add the landing page and main content panels
                mainPanel.add(landingPage, "landingPage");
                mainPanel.add(tabs, "mainPanel");

                // Set the main panel to be visible initially with the landing page
                cardLayout.show(mainPanel, "landingPage");

                // Handle transition when the "Start" button is clicked
                landingPage.setStartButtonListener(e -> cardLayout.show(mainPanel, "mainPanel"));

                // Add the main panel to the frame
                frame.add(mainPanel);
                frame.setLocationRelativeTo(null);  // Ensure window opens centered on the screen
                frame.setVisible(true);

                // Handle window closing event to save data
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
                JOptionPane.showMessageDialog(null, "Error loading data.");
            }
        });
    }
}
