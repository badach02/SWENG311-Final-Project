import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class RentCarPanel extends JPanel {
    private final DefaultListModel<Car> availableCarsModel;
    private final DefaultListModel<Car> rentedCarsModel;
    private final DefaultListModel<Customer> customerListModel;
    private final JList<Car> availableCarsList;
    private final JList<Car> rentedCarsList; // Added for rented cars with associated customers
    private final JList<Customer> customerList;
    private final CarManagerPanel carPanel;
    private final List<Receipt> receipts;
    private final List<Customer> customers;
    private final ReceiptManagerPanel receiptPanel;

    public RentCarPanel(CarManagerPanel carPanel, CustomerManagerPanel customerPanel, ReceiptManagerPanel receiptPanel) {
        this.carPanel = carPanel;
        this.receiptPanel = receiptPanel;
        this.receipts = receiptPanel.getReceipts();
        this.customers = customerPanel.getCustomers();
        setLayout(new BorderLayout());
    
        // Models
        availableCarsModel = new DefaultListModel<>();
        rentedCarsModel = new DefaultListModel<>();
        customerListModel = new DefaultListModel<>();
        availableCarsList = new JList<>(availableCarsModel);
        rentedCarsList = new JList<>(rentedCarsModel);
        customerList = new JList<>(customerListModel);
    
        updateLists();
    
        // Buttons
        JButton rentButton = new JButton("Rent Car");
        rentButton.setPreferredSize(new Dimension(200, 40));  // Set fixed size for the button
        rentButton.addActionListener(e -> rentCar());
    
        JButton returnButton = new JButton("Return Car");
        returnButton.setPreferredSize(new Dimension(200, 40));  // Set fixed size for the button
        returnButton.addActionListener(e -> returnCar());
    
        // Rent Section layout (available cars and customer list with Rent button)
        JPanel rentSection = new JPanel();
        rentSection.setLayout(new BorderLayout());
    
        // Rent panel holds available cars, customer list, and rent button
        JPanel rentPanel = new JPanel();
        rentPanel.setLayout(new BoxLayout(rentPanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment
    
        // Rent lists inside a horizontal GridLayout (1 row, 2 columns for lists)
        JPanel listsPanel = new JPanel(new GridLayout(1, 2));
        listsPanel.add(new JScrollPane(availableCarsList));
        listsPanel.add(new JScrollPane(customerList));
    
        rentPanel.add(listsPanel);  // Rent lists section
        rentPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add some spacing between list and button
        rentPanel.add(rentButton);  // Rent button at the bottom
    
        rentSection.add(rentPanel, BorderLayout.CENTER);
    
        // Return Section layout (rented cars list with Return button)
        JPanel returnSection = new JPanel(new BorderLayout());
        returnSection.add(new JScrollPane(rentedCarsList), BorderLayout.CENTER);
        returnSection.add(returnButton, BorderLayout.SOUTH);
    
        // Main panel with 2 sections (Rent and Return)
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));  // Divide space into 2 sections (Rent and Return)
        mainPanel.add(rentSection);
        mainPanel.add(returnSection);
    
        add(mainPanel, BorderLayout.CENTER);
    }

    public void updateLists() {
        // Update the available cars list (only cars that aren't rented)
        availableCarsModel.clear();
        for (Car car : carPanel.getCars()) {
            if (!car.isRented()) {
                availableCarsModel.addElement(car); // Only available cars for rent
            }
        }
    
        // Update the customer list
        customerListModel.clear();
        for (Customer customer : customers) {
            customerListModel.addElement(customer);
        }
    
        // Update the rented cars list with associated customers

        rentedCarsModel.clear();
        for (Car car : carPanel.getCars()) {
            if (car.isRented()) {
                rentedCarsModel.addElement(car); // Add rented car with customer information
            }
        }
    }
    

    public void rentCar() {
        Car selectedCar = availableCarsList.getSelectedValue();
        Customer selectedCustomer = customerList.getSelectedValue();
    
        if (selectedCar != null && selectedCustomer != null) {
            String priceInput = JOptionPane.showInputDialog(this, "Enter the rental price for the car:");
            try {
                double price = Double.parseDouble(priceInput);
    
                // Create a new receipt for the rental
                Receipt receipt = new Receipt(selectedCustomer, selectedCar, price);
                receipts.add(receipt); // Add the receipt to the list
    
                // Update the receipt panel
                receiptPanel.updateReceiptsList();  // <-- Ensure this is called
    
                // Mark car as rented and associate it with the customer
                selectedCar.setRented(true);
                selectedCar.setRenter(selectedCustomer);
    
                // Update the car panel and refresh lists
                carPanel.refreshTable();
                updateLists();
    
                JOptionPane.showMessageDialog(this, "Car rented successfully for $" + price);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price entered.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a car and a customer.");
        }
    }
    
    public void returnCar() {
        // Get the selected rented car
        Car selectedRentedCar = rentedCarsList.getSelectedValue();
    
        if (selectedRentedCar != null) {
            Customer renter = selectedRentedCar.getRenter();
            String renterName = renter.getName();
            String carModel = selectedRentedCar.getModel();
            boolean carReturned = false;
    
            System.out.println("Selected car: " + selectedRentedCar);
            System.out.println("Selected car renter: " + renter);
    
            // Remove the receipt and mark the car as not rented
            for (Receipt receipt : receipts) {
                System.out.println("Checking receipt: " + receipt);
                if (receipt.getCustomer().getName().equals(renterName) && receipt.getCar().getModel().equals(carModel)) {
                    // Mark the car as not rented and clear the renter
                    selectedRentedCar.setRented(false);
                    selectedRentedCar.setRenter(null);
                    receipts.remove(receipt);
                    carReturned = true;
                    break;
                }
            }
    
            // If the car was successfully returned
            if (carReturned) {
                try {
                    FileManager.saveCars(carPanel.getCars());  // Save the updated car list
                    FileManager.saveReceipts(receipts);        // Save updated receipts list
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
    
                carPanel.refreshTable();  // Refresh the car table in the panel
                updateLists();  // Refresh the available and rented cars list in the RentCarPanel
                JOptionPane.showMessageDialog(this, "Car returned successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No rental record found for this car and customer.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a car to return.");
        }
    }
}
