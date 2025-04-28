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

        // Form to add a new car
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 4)); // Grid layout for the input fields
        inputPanel.add(new JLabel("Make:"));
        makeField = new JTextField();
        inputPanel.add(makeField);
        inputPanel.add(new JLabel("Model:"));
        modelField = new JTextField();
        inputPanel.add(modelField);
        inputPanel.add(new JLabel("Year:"));
        yearField = new JTextField();
        inputPanel.add(yearField);

        addButton = new JButton("Add Car");
        inputPanel.add(addButton);

        removeButton = new JButton("Remove Selected Car");
        inputPanel.add(removeButton); // Add remove button

        add(inputPanel, BorderLayout.SOUTH);

        // Add button listener to add the car to the list
        addButton.addActionListener(e -> addCar());

        // Remove button listener to remove the selected car from the list
        removeButton.addActionListener(e -> removeSelectedCar());

        refreshTable(); // Initial table refresh
    }

    public List<Car> getCars() {
        return cars;
    }

    public void refreshTable() {
        tableModel.setRowCount(0); // Clear the table

        for (Car car : cars) {
            tableModel.addRow(new Object[]{
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.isRented() ? "Rented" : "Available",
                removeButton // Add the remove button to the table
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
            Car newCar = new Car(make, model, year); // Create a new Car object

            // Add the new car to the list
            cars.add(newCar);
            refreshTable(); // Refresh the table to show the new car

            // Clear the input fields
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

        // If no row is selected, show a message
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a car to remove.");
            return;
        }

        // Get the car object from the selected row (assuming row index matches the car's position in the list)
        Car carToRemove = cars.get(selectedRow);

        // Remove the car from the list
        cars.remove(carToRemove);
        refreshTable(); // Refresh the table to reflect the removal
        rentPanel.updateLists();
    }

    public void setRentPanel(RentCarPanel rentPanel){
        this.rentPanel = rentPanel;
    }
}
