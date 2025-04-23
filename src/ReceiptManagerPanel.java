// ReceiptManagerPanel.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

class ReceiptManagerPanel extends JPanel {
    private DefaultListModel<Receipt> receiptListModel;
    private JList<Receipt> receiptJList;
    private CustomerManagerPanel customerPanel;
    private CarManagerPanel carPanel;
    private List<Receipt> receipts;

    public ReceiptManagerPanel(CustomerManagerPanel customerPanel, CarManagerPanel carPanel, List<Receipt> receipts) {
        this.customerPanel = customerPanel;
        this.carPanel = carPanel;
        this.receipts = receipts;
    
        receiptListModel = new DefaultListModel<>();
        for (Receipt r : receipts) {
            receiptListModel.addElement(r);
        }
    
        receiptJList = new JList<>(receiptListModel);
        JScrollPane scrollPane = new JScrollPane(receiptJList);
    
        JButton rentButton = new JButton("Rent Car");
        rentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rentCar();
            }
        });
    
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(rentButton);
        add(panel, BorderLayout.SOUTH);
    }
    

    private void rentCar() {
        List<Customer> customers = customerPanel.getCustomers();  // Get latest customers
        List<Car> cars = carPanel.getCars();  // Get latest cars
    
        Customer customer = (Customer) JOptionPane.showInputDialog(
            this, "Choose Customer:", "Rent Car",
            JOptionPane.PLAIN_MESSAGE, null,
            customers.toArray(), customers.isEmpty() ? null : customers.get(0));
    
        Car car = (Car) JOptionPane.showInputDialog(
            this, "Choose Car:", "Rent Car",
            JOptionPane.PLAIN_MESSAGE, null,
            cars.toArray(), cars.isEmpty() ? null : cars.get(0));
    
        if (customer != null && car != null) {
            String priceStr = JOptionPane.showInputDialog("Price:");
            try {
                double price = Double.parseDouble(priceStr);
                Receipt receipt = new Receipt(customer, car, price);
                receiptListModel.addElement(receipt);
                receipts.add(receipt);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid number.");
            }
        }
    }
    

    public List<Receipt> getReceipts() {
        return Collections.list(receiptListModel.elements());
    }
}