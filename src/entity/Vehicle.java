package entity;

public class Vehicle {

    private int id;
    private int customerId;
    private String vehicleNumber;
    private String model;

    public Vehicle() {
    }

    public Vehicle(int id, int customerId, String vehicleNumber, String model) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleNumber = vehicleNumber;
        this.model = model;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {

        return "[Vehicle id=" + id +
                ", customerId=" + customerId +
                ", vehicleNumber=" + vehicleNumber +
                ", model=" + model +
                "]";
    }
}