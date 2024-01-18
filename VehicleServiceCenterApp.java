import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeSet;

class Vehicle implements Comparable<Vehicle> {
    String regNumber;
    String vehicleType;
    String vehicleMake;
    String ownerContactNumber;
    double serviceCost;

    public Vehicle(String regNumber, String vehicleType, String vehicleMake, String ownerContactNumber, double serviceCost) {
        this.regNumber = regNumber;
        this.vehicleType = vehicleType;
        this.vehicleMake = vehicleMake;
        this.ownerContactNumber = ownerContactNumber;
        this.serviceCost = serviceCost;
    }

    @Override
    public String toString() {
        return "Reg Number: " + regNumber +
                ", Type: " + vehicleType +
                ", Make: " + vehicleMake +
                ", Owner Contact: " + ownerContactNumber +
                ", Service Cost: " + serviceCost;
    }

    @Override
    public int compareTo(Vehicle other) {
        return Double.compare(this.serviceCost, other.serviceCost);
    }
}

class ServiceManagement {
    TreeSet<Vehicle> vehicleSet = new TreeSet<>();

    public void addVehicle(Vehicle vehicle) {
        vehicleSet.add(vehicle);
    }

    public double getServiceCost(String regNumber) throws VehicleNotFoundException {
        for (Vehicle vehicle : vehicleSet) {
            if (vehicle.regNumber.equals(regNumber)) {
                return vehicle.serviceCost;
            }
        }
        throw new VehicleNotFoundException("Vehicle not found");
    }

    public ArrayList<Vehicle> listVehicles() {
        return new ArrayList<>(vehicleSet);
    }
}

class VehicleNotFoundException extends Exception {
    public VehicleNotFoundException(String message) {
        super(message);
    }
}

public class VehicleServiceCenterApp extends JFrame {

    ServiceManagement serviceManagement = new ServiceManagement();

    JTextField regNumberField = new JTextField();
    JTextField vehicleTypeField = new JTextField();
    JTextField vehicleMakeField = new JTextField();
    JTextField ownerContactNumberField = new JTextField();
    JTextField serviceCostField = new JTextField();

    JTextArea outputArea = new JTextArea();

    public VehicleServiceCenterApp() {
        setTitle("Vehicle Service Center");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createGUI();
    }

    private void createGUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton addVehicleButton = new JButton("Add Vehicle");
        addVehicleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addVehicle();
            }
        });

        JButton getServiceCostButton = new JButton("Get Service Cost");
        getServiceCostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getServiceCost();
            }
        });

        JButton listVehiclesButton = new JButton("List Vehicles");
        listVehiclesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listVehicles();
            }
        });

        panel.add(new JLabel("Reg Number:"));
        panel.add(regNumberField);
        panel.add(new JLabel("Vehicle Type:"));
        panel.add(vehicleTypeField);
        panel.add(new JLabel("Vehicle Make:"));
        panel.add(vehicleMakeField);
        panel.add(new JLabel("Owner Contact Number:"));
        panel.add(ownerContactNumberField);
        panel.add(new JLabel("Service Cost:"));
        panel.add(serviceCostField);

        panel.add(addVehicleButton);
        panel.add(getServiceCostButton);
        panel.add(listVehiclesButton);

        panel.add(new JScrollPane(outputArea));

        add(panel);
    }

    private void addVehicle() {
        try {
            String regNumber = regNumberField.getText();
            String vehicleType = vehicleTypeField.getText();
            String vehicleMake = vehicleMakeField.getText();
            String ownerContactNumber = ownerContactNumberField.getText();
            double serviceCost = Double.parseDouble(serviceCostField.getText());

            Vehicle vehicle = new Vehicle(regNumber, vehicleType, vehicleMake, ownerContactNumber, serviceCost);
            serviceManagement.addVehicle(vehicle);
            showMessage("Vehicle added successfully.");
        } catch (NumberFormatException ex) {
            showMessage("Invalid service cost. Please enter a valid number.");
        }
    }

    private void getServiceCost() {
        String regNumber = regNumberField.getText();
        try {
            double serviceCost = serviceManagement.getServiceCost(regNumber);
            showMessage("Service cost for " + regNumber + ": " + serviceCost);
        } catch (VehicleNotFoundException ex) {
            showMessage("Vehicle not found.");
        }
    }

    private void listVehicles() {
        ArrayList<Vehicle> vehicles = serviceManagement.listVehicles();
        outputArea.setText("");
        for (Vehicle vehicle : vehicles) {
            outputArea.append(vehicle.toString() + "\n");
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VehicleServiceCenterApp().setVisible(true);
            }
        });
    }
}
