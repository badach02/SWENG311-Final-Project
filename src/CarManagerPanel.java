import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.List;

public class CarManagerPanel extends JPanel {
    private List<Car> cars;
    private JTable carTable;
    private DefaultTableModel tableModel;

    public CarManagerPanel(List<Car> cars) {
        this.cars = cars;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        String[] columnNames = {"Model", "Year", "Rented", "Renter"};
        tableModel = new DefaultTableModel(columnNames, 0);

        carTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(carTable);
        add(scrollPane, BorderLayout.CENTER);

        refreshTable();
    }

    public List<Car> getCars() {
        return cars;
    }

    public void refreshTable() {
        tableModel.setRowCount(0); 

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
