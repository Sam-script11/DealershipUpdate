package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContractDataManager {

    private final BasicDataSource dataSource;

    public ContractDataManager(String username, String password) {
        this.dataSource = new BasicDataSource();
        this.dataSource.setUrl("jdbc:mysql://localhost:3306/dealership_workshop");
        this.dataSource.setUsername(username);
        this.dataSource.setPassword(password);
    }

    public void getContracts(Dealership dealership) {
        try (Connection connection = dataSource.getConnection()) {
            //Try block handles lease_contract table
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT * FROM lease_contracts""");
                 ResultSet results = preparedStatement.executeQuery()) {

                while (results.next()) {
                    String date = results.getString("date");
                    int vin = results.getInt("VIN");
                    ;
                    String customerName = results.getString("CustomerName");
                    String email = results.getString("Email");
                    double totalPrice = results.getDouble("TotalPrice");
                    double monthlyPayment = results.getDouble("MonthlyPayment");
                    double expectedEndingValue = results.getDouble("ExpectedEndingValue");
                    double leaseFee = results.getDouble("LeaseFee");

                    dealership.addContract(new Lease(
                            date,
                            customerName,
                            vin,
                            email,
                            false,
                            totalPrice,
                            monthlyPayment,
                            expectedEndingValue,
                            leaseFee));
                }

            } catch (SQLException e) {
                throw new SQLException(e);
            }

            //Try block handles sales_contract table
            try (PreparedStatement preparedStatement = connection.prepareStatement("""
                    SELECT * FROM sales_contracts""");
                 ResultSet results = preparedStatement.executeQuery()) {

                while (results.next()) {
                    String date = results.getString("date");
                    String customerName = results.getString("CustomerName");
                    int vin = results.getInt("VIN");
                    String email = results.getString("Email");
                    double totalPrice = results.getDouble("TotalPrice");
                    double monthlyPayment = results.getDouble("MonthlyPayment");
                    double salesTax = results.getDouble("SalesTaxAmount");
                    int recordingFee = results.getInt("RecordingFee");
                    int processingFee = results.getInt("ProcessingFee");
                    boolean financing = results.getBoolean("WantToFinance");

                    dealership.addContract(new Sale(date,
                            customerName,
                            vin,
                            email,
                            true,
                            totalPrice,
                            monthlyPayment,
                            salesTax,
                            recordingFee,
                            processingFee,
                            financing));
                }

            } catch (SQLException e) {
                throw new SQLException(e);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveContract(Contract contract, int VIN) {
//            String contractType, date, customerName, email, make = "", model = "", vehicleType = "", color = "";
//            int vin = 0, odometer = 0, year = 0;
//            double price = 0, salesTax, recordingFee, processingFee, totalPrice, monthlyPayment;
//            boolean financing, vehicleSold;

        try (Connection connection = dataSource.getConnection()) {

            if (contract instanceof Sale) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("""
                         INSERT INTO sales_contracts (date, VIN, CustomerName, Email, VehicleSold, TotalPrice,\s
                         MonthlyPayment, SalesTaxAmount, RecordingFee, ProcessingFee, WantToFinance )
                         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                        \s""", PreparedStatement.RETURN_GENERATED_KEYS)) {

                    preparedStatement.setString(1, contract.getDate());
                    preparedStatement.setInt(2, VIN);
                    preparedStatement.setString(3, contract.getCustomerName());
                    preparedStatement.setString(4, contract.getEmail());
                    preparedStatement.setBoolean(5, contract.isVehicleSold());
                    preparedStatement.setDouble(6, contract.getTotalPrice());
                    preparedStatement.setDouble(7, contract.getMonthlyPayment());
                    preparedStatement.setDouble(8, ((Sale) contract).getSalesTaxAmount());
                    preparedStatement.setInt(9, ((Sale) contract).getRecordingFee());
                    preparedStatement.setInt(10, ((Sale) contract).getProcessingFee());
                    preparedStatement.setBoolean(11, ((Sale) contract).isWantToFinance());

                    int rows = preparedStatement.executeUpdate();

                    System.out.printf("Rows updated: %d\n", rows);

                    try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                        while (keys.next()) {
                            System.out.printf("%d key was added\n", keys.getInt(1));
                        }

                    } catch (SQLException e) {
                        throw new SQLException(e);
                    }
                }
            } else if (contract instanceof Lease) {
                try (PreparedStatement preparedStatement = connection.prepareStatement("""
                        INSERT INTO lease_contracts (date, VIN, CustomerName, Email, VehicleSold, TotalPrice, MonthlyPayment,
                        ExpectedEndingValue, LeaseFee)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """, PreparedStatement.RETURN_GENERATED_KEYS)) {

                    preparedStatement.setString(1, contract.getDate());
                    preparedStatement.setInt(2, VIN);
                    preparedStatement.setString(3, contract.getCustomerName());
                    preparedStatement.setString(4, contract.getEmail());
                    preparedStatement.setBoolean(5, contract.isVehicleSold());
                    preparedStatement.setDouble(6, contract.getTotalPrice());
                    preparedStatement.setDouble(7, contract.getMonthlyPayment());
                    preparedStatement.setDouble(8, ((Lease) contract).getExpectedEndingValue());
                    preparedStatement.setDouble(9, ((Lease) contract).getLeaseFee());

                    int rows = preparedStatement.executeUpdate();

                    System.out.printf("Rows updated: %d\n", rows);

                    try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                        while (keys.next()) {
                            System.out.printf("%d key was added\n", keys.getInt(1));
                        }

                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}

