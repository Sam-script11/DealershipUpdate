package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DealerShipDataManager {
    private final BasicDataSource dataSource;

    public DealerShipDataManager(String username, String password) {
        this.dataSource = new BasicDataSource();
        this.dataSource.setUrl("jdbc:mysql://localhost:3306/dealership_workshop");
        this.dataSource.setUsername(username);
        this.dataSource.setPassword(password);
    }

    public Dealership getDealership() {
        Dealership dealership = new Dealership();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("""
                     SELECT * FROM Vehicles
                     """)
        ) {
            try (ResultSet results = preparedStatement.executeQuery()) {

                while (results.next()) {
                    int vin = Integer.parseInt(results.getString("VIN"));
                    int year = Integer.parseInt(results.getString("Year"));
                    String make = results.getString("Make");
                    String model = results.getString("Model");
                    String vehicleType = results.getString("VehicleType");
                    String color = results.getString("Color");
                    int odometer = Integer.parseInt(results.getString("Odometer"));
                    double price = Double.parseDouble(results.getString("Price"));
                    dealership.addVehicle(new Vehicle(vin, year, odometer, make, model, vehicleType, color, price));
                }

            } catch (SQLException e) {
                System.out.println("Error adding vehicles to dealership:" + e);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dealership;
    }

    public void addToInventory(Vehicle vehicle) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("""
                     INSERT INTO Vehicles
                     VALUES (?,?, ? ,?, ?, ?, ?, ?)
                     """, PreparedStatement.RETURN_GENERATED_KEYS)) {

            int vin = vehicle.getVin();
            int year = vehicle.getYear();
            String make = vehicle.getMake();
            String model = vehicle.getModel();
            String vehicleType = vehicle.getVehicleType();
            String color = vehicle.getColor();
            int odometer = vehicle.getOdometer();
            double price = vehicle.getPrice();

            preparedStatement.setInt(1, vin);
            preparedStatement.setInt(2, year);
            preparedStatement.setString(3, make);
            preparedStatement.setString(4, model);
            preparedStatement.setString(5, vehicleType);
            preparedStatement.setString(6, color);
            preparedStatement.setInt(7, odometer);
            preparedStatement.setDouble(8, price);

            int rows = preparedStatement.executeUpdate();

            System.out.printf("Rows updated: %d\n", rows);

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                while (keys.next()) {
                    System.out.printf("%d key was added\n", keys.getInt(1));
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeFromInventory(Vehicle vehicle) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("""
                     DELETE FROM Vehicles
                     WHERE VIN = ?
                     """)) {

            preparedStatement.setInt(1, vehicle.getVin());

            int rows = preparedStatement.executeUpdate();

            System.out.printf("Rows deleted: %d\n", rows);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

