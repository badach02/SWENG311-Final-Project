import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomerManagerPanel extends JPanel {
    private List<Customer> customers;
    private DefaultTableModel tableModel;
    private List<Receipt> receiptList;
    private JTable customerTable;
    private RentCarPanel rentPanel;

    public CustomerManagerPanel(List<Customer> customers, List<Receipt> receipts) {
        this.receiptList = receipts;
        this.customers = customers;

        setupUI();
        refreshTable();
    }

    public void setupUI(){
        String[] columnNames = {"ID", "Name"};
        tableModel = new DefaultTableModel(columnNames, 0);
        customerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Customer");
        JButton removeButton = new JButton("Remove Customer");

        addButton.addActionListener(e -> addCustomer());
        removeButton.addActionListener(e -> removeCustomer());

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addCustomer(){
        String name = JOptionPane.showInputDialog("Enter Customer Name:");
        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Customer ID:"));
        boolean fail = false;

        for(Customer customer: getCustomers()){
            if(name.equals(customer.getName()) || customer.getId() == id){
                fail = true;
            }
        }
        
        if(fail == true){
            JOptionPane.showMessageDialog(this, "Name or ID already exists");
        }else{
            customers.add(new Customer(name, id));
        }
        rentPanel.updateLists();
        refreshTable();
    }

    public void removeCustomer(){
        int selectedRow = customerTable.getSelectedRow();
        Customer selectedCustomer = customers.get(selectedRow);
        boolean rented = false;

        if (selectedCustomer != null) {

            for(Receipt receipt : receiptList){ 
                if(receipt.toString().contains(selectedCustomer.getName())){
                    System.out.println("Found " + selectedCustomer.getName());
                    rented = true;
                }
            }

            if (rented == true) {
                JOptionPane.showMessageDialog(this, "Customer is currently renting a car and cannot be removed.");
            } else {
                System.out.println("Removed customer");
                customers.remove(selectedCustomer);
            }
        }
        rentPanel.updateLists();
        refreshTable();
    }

    public void refreshTable() {
        tableModel.setRowCount(0);

        if(customers != null){
            for (Customer customer : customers) {
                tableModel.addRow(new Object[]{
                    customer.getId(),
                    customer.getName()
                });
            }
        }
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setRentPanel(RentCarPanel rentPanel){
        this.rentPanel = rentPanel;
    }
}