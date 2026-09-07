package service;

import config.DbConfig;
import entity.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleService {

    public void addVehicle(Vehicle vehicle) throws SQLException {

        Connection conn = DbConfig.getConnection();

        String sql = "INSERT INTO vehicle(customer_id, vehicle_number, model) " +
                "VALUES (?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, vehicle.getCustomerId());
        ps.setString(2, vehicle.getVehicleNumber());
        ps.setString(3, vehicle.getModel());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    public Vehicle getVehicleByNumber(String vehicleNumber) throws SQLException {

        Connection conn = DbConfig.getConnection();

        String sql = "SELECT * FROM vehicle WHERE vehicle_number = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, vehicleNumber);

        ResultSet rs = ps.executeQuery();

        Vehicle vehicle = null;

        if (rs.next()) {

            vehicle = new Vehicle(
                    rs.getInt("id"),
                    rs.getInt("customer_id"),
                    rs.getString("vehicle_number"),
                    rs.getString("model")
            );
        }

        rs.close();
        ps.close();
        conn.close();

        return vehicle;
    }

    public Vehicle getVehicleById(int id) throws SQLException {

        Connection conn = DbConfig.getConnection();

        String sql = "SELECT * FROM vehicle WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        Vehicle vehicle = null;

        if (rs.next()) {

            vehicle = new Vehicle(
                    rs.getInt("id"),
                    rs.getInt("customer_id"),
                    rs.getString("vehicle_number"),
                    rs.getString("model")
            );
        }

        rs.close();
        ps.close();
        conn.close();

        return vehicle;
    }

    public List<Vehicle> getVehiclesByCustomerId(int customerId)
            throws SQLException {

        Connection conn = DbConfig.getConnection();

        List<Vehicle> list = new ArrayList<>();

        String sql = "SELECT * FROM vehicle WHERE customer_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, customerId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            Vehicle vehicle = new Vehicle(
                    rs.getInt("id"),
                    rs.getInt("customer_id"),
                    rs.getString("vehicle_number"),
                    rs.getString("model")
            );

            list.add(vehicle);
        }

        rs.close();
        ps.close();
        conn.close();

        return list;
    }
}