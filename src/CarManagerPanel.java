import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.List;

public class CarManagerPanel extends JPanel {
    private List<Car> cars;
    private JTable carTable;
    private DefaultTableModel tableModel;
    private JTextField makeField, modelField, yearField;
    private JButton addButton, removeButton;
    private RentCarPanel rentPanel;

    public CarManagerPanel(List<Car> cars) {
        this.cars = cars;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
    
        // Table setup
        String[] columnNames = {"Make", "Model", "Year", "Rented"};
        tableModel = new DefaultTableModel(columnNames, 0);
        carTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(carTable);
        add(scrollPane, BorderLayout.CENTER);
        
        // === Input fields panel ===
        JPanel inputFieldsPanel = new JPanel(new GridLayout(1, 6, 10, 0));
        inputFieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 10));
        
        inputFieldsPanel.add(new JLabel("Make:"));
        makeField = new JTextField();
        inputFieldsPanel.add(makeField);
        
        inputFieldsPanel.add(new JLabel("Model:"));
        modelField = new JTextField();
        inputFieldsPanel.add(modelField);
        
        inputFieldsPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        inputFieldsPanel.add(yearField);
        
        // === Buttons panel ===
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Align buttons left
        addButton = new JButton("Add Car");
        removeButton = new JButton("Remove Selected Car");
    
        buttonsPanel.add(addButton);
        buttonsPanel.add(removeButton);
        
        // === South panel combines both ===
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputFieldsPanel, BorderLayout.NORTH);
        southPanel.add(buttonsPanel, BorderLayout.SOUTH); // Add the buttons panel here
    
        add(southPanel, BorderLayout.SOUTH);
        
        // === Action listeners ===
        addButton.addActionListener(e -> addCar());
        removeButton.addActionListener(e -> removeSelectedCar());        
    
        refreshTable();
    }
    
    public List<Car> getCars() {
        return cars;
    }

    public void refreshTable() {
        tableModel.setRowCount(0);

        for (Car car : cars) {
            tableModel.addRow(new Object[]{
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.isRented() ? "Rented" : "Available",
                removeButton
            });
        }
    }

    private void addCar() {
        String make = makeField.getText();
        String model = modelField.getText();
        String yearStr = yearField.getText();

        if (make.isEmpty() || model.isEmpty() || yearStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        try {
            int year = Integer.parseInt(yearStr);
            Car newCar = new Car(make, model, year);
            cars.add(newCar);
            refreshTable();
            makeField.setText("");
            modelField.setText("");
            yearField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid year.");
        }
        rentPanel.updateLists();
    }

    private void removeSelectedCar() {
        int selectedRow = carTable.getSelectedRow();
        Car carToRemove = cars.get(selectedRow);

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a car to remove.");
            return;
        } else if(carToRemove.isRented() == true){
            JOptionPane.showMessageDialog(this, "Cannot delete a currently rented car.");
        } else{
            cars.remove(carToRemove);
            refreshTable();
        }

        rentPanel.updateLists();
    }

    public void setRentPanel(RentCarPanel rentPanel){
        this.rentPanel = rentPanel;
    }
}
