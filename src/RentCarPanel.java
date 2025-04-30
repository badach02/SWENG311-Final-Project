import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class RentCarPanel extends JPanel {
    private final DefaultListModel<Car> availableCarsModel;
    private final DefaultListModel<Car> rentedCarsModel;
    private final DefaultListModel<Customer> customerListModel;
    private final JList<Car> availableCarsList;
    private final JList<Car> rentedCarsList;
    private final JList<Customer> customerList;
    private final CarManagerPanel carPanel;
    private final List<Receipt> receipts;
    private final ReceiptManagerPanel receiptPanel;
    private final CustomerManagerPanel customerPanel;

    public RentCarPanel(CarManagerPanel carPanel, CustomerManagerPanel customerPanel, ReceiptManagerPanel receiptPanel) {
        this.carPanel = carPanel;
        this.receiptPanel = receiptPanel;
        this.customerPanel = customerPanel;
        this.receipts = receiptPanel.getReceipts();
        setLayout(new BorderLayout());

        availableCarsModel = new DefaultListModel<>();
        rentedCarsModel = new DefaultListModel<>();
        customerListModel = new DefaultListModel<>();
        availableCarsList = new JList<>(availableCarsModel);
        rentedCarsList = new JList<>(rentedCarsModel);
        customerList = new JList<>(customerListModel);
    
        updateLists();
    
        JButton rentButton = new JButton("Rent Car");
        JButton returnButton = new JButton("Return Car");

        Dimension buttonSize = new Dimension(200, 40);
        rentButton.setPreferredSize(buttonSize);
        returnButton.setPreferredSize(buttonSize);

        rentButton.addActionListener(e -> rentCar());
        returnButton.addActionListener(e -> returnCar());

        JPanel rentSection = new JPanel(new BorderLayout());

        JLabel rentHeader = new JLabel("Rent Car", SwingConstants.CENTER);
        rentHeader.setFont(new Font("SansSerif", Font.BOLD, 16));
        rentSection.add(rentHeader, BorderLayout.NORTH);

        JPanel rentPanel = new JPanel(new GridLayout(1, 2));
        rentPanel.add(new JScrollPane(availableCarsList));
        rentPanel.add(new JScrollPane(customerList));
        rentSection.add(rentPanel, BorderLayout.CENTER);

        rentSection.add(rentButton, BorderLayout.SOUTH);

        JPanel returnSection = new JPanel(new BorderLayout());

        JLabel returnHeader = new JLabel("Return Car", SwingConstants.CENTER);
        returnHeader.setFont(new Font("SansSerif", Font.BOLD, 16));
        returnSection.add(returnHeader, BorderLayout.NORTH);

        returnSection.add(new JScrollPane(rentedCarsList), BorderLayout.CENTER);
        returnSection.add(returnButton, BorderLayout.SOUTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        mainPanel.add(rentSection);
        mainPanel.add(returnSection);

        add(mainPanel, BorderLayout.CENTER);


    }

    public void updateLists() {
        availableCarsModel.clear();
        for (Car car : carPanel.getCars()) {
            if (!car.isRented()) {
                availableCarsModel.addElement(car);
            }
        }

        customerListModel.clear();
        for (Customer customer : customerPanel.getCustomers()) {
            customerListModel.addElement(customer);
        }

        rentedCarsModel.clear();
        for (Car car : carPanel.getCars()) {
            if (car.isRented()) {
                rentedCarsModel.addElement(car); 
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
                Receipt receipt = new Receipt(selectedCustomer, selectedCar, price);
                receipts.add(receipt);
                receiptPanel.updateReceiptsList();
                selectedCar.setRented(true);
                selectedCar.setRenter(selectedCustomer);
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
        Car selectedRentedCar = rentedCarsList.getSelectedValue();
    
        if (selectedRentedCar != null) {
            Customer renter = selectedRentedCar.getRenter();
            String renterName = renter.getName();
            String carModel = selectedRentedCar.getModel();
            boolean carReturned = false;
    
            System.out.println("Selected car: " + selectedRentedCar);
            System.out.println("Selected car renter: " + renter);
    
            for (Receipt receipt : receipts) {
                System.out.println("Checking receipt: " + receipt);
                if (receipt.getCustomer().getName().equals(renterName) && receipt.getCar().getModel().equals(carModel)) {
                    selectedRentedCar.setRented(false);
                    selectedRentedCar.setRenter(null);
                    receipts.remove(receipt);
                    receiptPanel.updateReceiptsList();
                    System.out.println("Deleted receipt: " + receipt);
                    carReturned = true;
                    break;
                }
            }

            if (carReturned) {
                try {
                    FileManager.saveCars(carPanel.getCars());
                    FileManager.saveReceipts(receipts); 
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
    
                carPanel.refreshTable();
                updateLists();
                JOptionPane.showMessageDialog(this, "Car returned successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No rental record found for this car and customer.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a car to return.");
        }
    }
}
