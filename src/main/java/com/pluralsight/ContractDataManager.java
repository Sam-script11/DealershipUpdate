package com.pluralsight;

import javax.swing.text.DefaultEditorKit;
import java.io.*;
import java.util.regex.Pattern;

public class ContractDataManager {

//Contract testCon = new Sale("10/31/2024","Sam",true,25000.00,0,125.00,25,25,false);

    public static void getContracts( Dealership dealership) {


        try {
            FileReader fr = new FileReader("src/main/resources/contracts.csv");
            // create a BufferedReader to manage input stream
            BufferedReader br = new BufferedReader(fr);
            String input;
            // read until there is no more data
            while ((input = br.readLine()) != null) {
                 //SALE|20210928|Dana Wyatt|dana@texas.com|10112|1993|Ford|Explorer|SUV|Red|525123|995.00|49.75|100.00|295.00|1439.75|NO|0.00

                if (input.startsWith("SALE")) {

                    String[] lineSplit = input.split(Pattern.quote("|"));
                    String contractType = lineSplit[0];
                    String date = lineSplit[1];
                    String customerName = lineSplit[2];
                    String email = lineSplit[3];
                    String vin = lineSplit[4];
                    int year = Integer.parseInt(lineSplit[5]);
                    String make = lineSplit[6];
                    String model = lineSplit[7];
                    String vehicleType = lineSplit[8];
                    String color = lineSplit[9];
                    int odometer = Integer.parseInt(lineSplit[10]);
                    double price = Double.parseDouble(lineSplit[11]);
                    double salesTax = Double.parseDouble(lineSplit[12]);
                    double recordingFee = Double.parseDouble(lineSplit[13]);
                    double processingFee= Double.parseDouble(lineSplit[14]);
                    double totalPrice = Double.parseDouble(lineSplit[15]);
                    boolean financing = Boolean.parseBoolean(lineSplit[16]);
                    double monthlyPayment = Double.parseDouble(lineSplit[17]);

                    int recordingFeeSale = (int) recordingFee;
                    int processingFeeSale = (int) processingFee;
                    dealership.addContract(new Sale(date,
                            customerName,
                            email,
                            true,
                            totalPrice,
                            monthlyPayment,
                            salesTax,
                            recordingFeeSale,
                            processingFeeSale,
                            financing));
                }else{
                    //LEASE|20210928|Zachary Westly|zach@texas.com|37846|2021|Chevrolet|Silverado|truck|Black|2750|31995.00|15997.50|2239.65|18337.15|541.39
                    String[] lineSplit = input.split(Pattern.quote("|"));
                    String contractType = lineSplit[0];
                    String date = lineSplit[1];
                    String customerName = lineSplit[2];
                    String email = lineSplit[3];
                    String vin = lineSplit[4];
                    int year = Integer.parseInt(lineSplit[5]);
                    String make = lineSplit[6];
                    String model = lineSplit[7];
                    String vehicleType = lineSplit[8];
                    String color = lineSplit[9];
                    int odometer = Integer.parseInt(lineSplit[10]);
                    double price = Double.parseDouble(lineSplit[11]);
                    double expectedEndingValue = Double.parseDouble(lineSplit[12]);
                    double leaseFee = Double.parseDouble(lineSplit[13]);
                    double totalPrice= Double.parseDouble(lineSplit[14]);
                    double monthlyPayment = Double.parseDouble(lineSplit[15]);

                    dealership.addContract(new Lease(
                            date,
                            customerName,
                            email,
                            false,
                            totalPrice,
                            monthlyPayment,
                            expectedEndingValue,
                            leaseFee));
                }
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void saveContract(Contract newContract) {
      //  Contract testCon = new Sale("10/31/2024","Sam",true,25000.00,0,125.00,25,25,false);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/contracts.csv"));
//String typeOfcontract = testCon.getClass().getSimpleName().toUpperCase();

            //Release file
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

