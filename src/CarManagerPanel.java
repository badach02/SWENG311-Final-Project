import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class CarManagerPanel extends JPanel {
    private DefaultListModel<Car> carListModel;
    private JList<Car> carList;

    public CarManagerPanel(List<Car> cars) {
        carListModel = new DefaultListModel<>();
        for (Car c : cars) {
            carListModel.addElement(c);
        }

        carList = new JList<>(carListModel);
        JScrollPane scrollPane = new JScrollPane(carList);

        JButton addButton = new JButton("Add Car");
        JButton removeButton = new JButton("Remove Car");

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String model = JOptionPane.showInputDialog("Enter Car Model:");
                int year = Integer.parseInt(JOptionPane.showInputDialog("Enter Car Year:"));
                carListModel.addElement(new Car(model, year));
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Car selectedCar = carList.getSelectedValue();
                if (selectedCar != null) {
                    carListModel.removeElement(selectedCar);
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

    public List<Car> getCars() {
        return Collections.list(carListModel.elements());
    }
}
