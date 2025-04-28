import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.List;

public class CarManagerPanel extends JPanel {
    private List<Car> cars;
    private JTable carTable;
    private DefaultTableModel tableModel;

    // Constructor accepts a List of cars
    public CarManagerPanel(List<Car> cars) {
        this.cars = cars;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Set up the table model with an additional "Renter" column
        String[] columnNames = {"Model", "Year", "Rented", "Renter"};
        tableModel = new DefaultTableModel(columnNames, 0);

        // Set up the table
        carTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(carTable);
        add(scrollPane, BorderLayout.CENTER);

        // Fill the table with data
        refreshTable();  // Call refresh to populate the table when it initializes
    }

    // Method to get the list of cars
    public List<Car> getCars() {
        return cars;
    }

    // Method to refresh the table and show all cars
    public void refreshTable() {
        tableModel.setRowCount(0); // Clear existing rows

        for (Car car : cars) {
            System.out.println(car.getRenter());
            String renter = car.isRented() && car.getRenter() != null ? car.getRenter().getName() : "Available";
        
            tableModel.addRow(new Object[]{
                car.getMake(),
                car.getModel(),
                car.getYear(),
                car.isRented() ? "Rented" : "Available",
                renter
            });
        }
    }
}
