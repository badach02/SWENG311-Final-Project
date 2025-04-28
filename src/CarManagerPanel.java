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

        String[] columnNames = {"Make", "Model", "Year", "Rented"};
        tableModel = new DefaultTableModel(columnNames, 0);
        carTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(carTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 4));
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
        inputPanel.add(removeButton);
        add(inputPanel, BorderLayout.SOUTH);

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
