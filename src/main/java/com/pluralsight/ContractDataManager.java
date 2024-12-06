package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
            //Try block handles lease table
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

            } catch(SQLException e){
                throw new SQLException(e);
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void saveLeaseContract (Contract contract){
            String contractType, date, customerName, email, make = "", model = "", vehicleType = "", color = "";
            int vin = 0, odometer = 0, year = 0;
            double price = 0, salesTax, recordingFee, processingFee, totalPrice, monthlyPayment;
            boolean financing, vehicleSold;

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/contracts.csv"));

                for (Contract c : dealership.getContracts()) {
                    if (contract instanceof Sale) {
                        contractType = "SALE";
                        date = contract.getDate();
                        customerName = contract.getCustomerName();
                        email = contract.getEmail();
                        vehicleSold = contract.isVehicleSold();
                        totalPrice = contract.getTotalPrice();
                        monthlyPayment = contract.getMonthlyPayment();
                        salesTax = ((Sale) contract).getSalesTaxAmount();
                        recordingFee = ((Sale) contract).getRecordingFee();
                        processingFee = ((Sale) contract).getProcessingFee();
                        financing = ((Sale) contract).isWantToFinance();

                        for (Vehicle vehicle : dealership.getAllVehicles()) {
                            vin = vehicle.getVin();
                            year = vehicle.getYear();
                            make = vehicle.getMake();
                            model = vehicle.getModel();
                            vehicleType = vehicle.getVehicleType();
                            color = vehicle.getColor();
                            odometer = vehicle.getOdometer();
                            price = vehicle.getPrice();
                        }

                        //SALE|20210928|Dana Wyatt|dana@texas.com|10112|1993|Ford|Explorer|SUV|Red|525123|995.00|49.75|100.00|295.00|1439.75|NO|0.00
                        String saleEntry = String.format("%s|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f\n",
                                contractType,
                                date,
                                customerName,
                                email,
                                vin,
                                year,
                                make,
                                model,
                                vehicleType,
                                color,
                                odometer,
                                price,
                                salesTax,
                                recordingFee,
                                processingFee,
                                totalPrice,
                                financing,
                                monthlyPayment
                        );

                        bw.write(saleEntry);

                    } else if (contract instanceof Lease) {

                        contractType = "LEASE";
                        date = contract.getDate();
                        customerName = contract.getCustomerName();
                        email = contract.getEmail();
                        vehicleSold = contract.isVehicleSold();
                        totalPrice = contract.getTotalPrice();
                        monthlyPayment = contract.getMonthlyPayment();
                        double expectedEndingValue = ((Lease) contract).getExpectedEndingValue();
                        double leaseFee = ((Lease) contract).getLeaseFee();

                        for (Vehicle vehicle : dealership.getAllVehicles()) {
                            vin = vehicle.getVin();
                            year = vehicle.getYear();
                            make = vehicle.getMake();
                            model = vehicle.getModel();
                            vehicleType = vehicle.getVehicleType();
                            color = vehicle.getColor();
                            odometer = vehicle.getOdometer();
                            price = vehicle.getPrice();
                        }

                        //LEASE|20210928|Zachary Westly|zach@texas.com|37846|2021|Chevrolet|Silverado|truck|Black|2750|31995.00|15997.50|2239.65|18337.15|541.39
                        String leaseEntry = String.format("%s|%s|%s|%s|%d|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f\n",
                                contractType,
                                date,
                                customerName,
                                email,
                                vin,
                                year,
                                make,
                                model,
                                vehicleType,
                                color,
                                odometer,
                                price,
                                expectedEndingValue,
                                leaseFee,
                                totalPrice,
                                monthlyPayment
                        );

                        bw.write(leaseEntry);
                    }
                }

                //Release file
                bw.close();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

