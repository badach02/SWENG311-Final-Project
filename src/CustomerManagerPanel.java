import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CustomerManagerPanel extends JPanel {
    private DefaultListModel<Customer> customerListModel;
    private JList<Customer> customerList;

    public CustomerManagerPanel(List<Customer> customers) {
        customerListModel = new DefaultListModel<>();
        for (Customer c : customers) {
            customerListModel.addElement(c);
        }

        customerList = new JList<>(customerListModel);
        JScrollPane scrollPane = new JScrollPane(customerList);

        JButton addButton = new JButton("Add Customer");
        JButton removeButton = new JButton("Remove Customer");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog("Enter Customer Name:");
                int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Customer ID:"));
                customerListModel.addElement(new Customer(name, id));
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Customer selectedCustomer = customerList.getSelectedValue();
                if (selectedCustomer != null) {
                    customerListModel.removeElement(selectedCustomer);
                }
            }
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
}
