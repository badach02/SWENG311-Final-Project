import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class CustomerManagerPanel extends JPanel {
    private DefaultListModel<Customer> customerListModel;
    private JList<Customer> customerList;
    private List<Receipt> receiptList;
    private RentCarPanel rentPanel;

    public CustomerManagerPanel(List<Customer> customers, List<Receipt> receipts) {
        this.receiptList = receipts;
        customerListModel = new DefaultListModel<>();
        for (Customer c : customers) {
            customerListModel.addElement(c);
        }

        customerList = new JList<>(customerListModel);
        JScrollPane scrollPane = new JScrollPane(customerList);

        JButton addButton = new JButton("Add Customer");
        JButton removeButton = new JButton("Remove Customer");

        addButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter Customer Name:");
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Customer ID:"));
            customerListModel.addElement(new Customer(name, id));
            rentPanel.updateLists();
        });

        removeButton.addActionListener(e -> {
            Customer selectedCustomer = customerList.getSelectedValue();
            if (selectedCustomer != null) {
                boolean isRenting = receiptList.stream().anyMatch(r -> r.getCustomer().equals(selectedCustomer) && r.getCar().isRented());
                if (isRenting) {
                    JOptionPane.showMessageDialog(this, "Customer is currently renting a car and cannot be removed.");
                } else {
                    customerListModel.removeElement(selectedCustomer);
                }
            }
            rentPanel.updateLists();
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public List<Customer> getCustomers() {
        return Collections.list(customerListModel.elements());
    }

    public void setRentPanel(RentCarPanel rentPanel){
        this.rentPanel = rentPanel;
    }
}