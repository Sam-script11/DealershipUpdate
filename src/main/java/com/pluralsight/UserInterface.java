package com.pluralsight;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class UserInterface {

    private final Dealership dealership;
    private final DealerShipDataManager dealerShipFileManager;
    private final ContractDataManager contractDataManager;

    public UserInterface(String[] args) {
        String username = args[0];
        String password = args[1];
        dealerShipFileManager = new DealerShipDataManager(username, password);
        dealership = dealerShipFileManager.getDealership();
        contractDataManager = new ContractDataManager(username, password);

    }



    static final Scanner keyboard = new Scanner(System.in);

    public static void display() {
        System.out.print("""
                1 - Find vehicles within a price range
                2 - Find vehicles by make / model
                3 - Find vehicles by year range
                4 - Find vehicles by color
                5 - Find vehicles by mileage range
                6 - Find vehicles by type (car, truck, SUV, van)
                7 - List ALL vehicles
                8 - Add a vehicle
                9 - Remove a vehicle
                10 - Contract Screen
                99 - Quit
                Please enter your selection:""");
    }

    public void processGetByPriceRequest() {
        System.out.print("Please enter the minimum price: ");
        double min = Double.parseDouble(keyboard.nextLine().trim());

        System.out.print("Please enter the maximum price: ");
        double max = Double.parseDouble(keyboard.nextLine().trim());

//        System.out.println(Dealership.GetVehiclesByPrice(min, max));

        for (Vehicle vehicle : dealership.getVehicleByPrice(min, max)) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");
        }

    }

    public void processGetByMakeModelRequest() {
        System.out.print("Please enter the make of the vehicle: ");
        String make = keyboard.nextLine().trim();

        System.out.print("Please enter the model of the vehicle: ");
        String model = keyboard.nextLine().trim();

        for (Vehicle vehicle : dealership.getVehicleByMakeMode(make, model)) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");

        }
    }

    public void processGetByYearRequest() {
        System.out.print("Please enter the minimum year of the vehicle: ");
        int minYear = Integer.parseInt(keyboard.nextLine().trim());

        System.out.print("Please enter the maximum year of the vehicle: ");
        int maxYear = Integer.parseInt(keyboard.nextLine().trim());

        for (Vehicle vehicle : dealership.getVehiclesByYear(minYear, maxYear)) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");
        }
    }

    public void processGetByColorRequest() {
        System.out.print("Please enter the color of the vehicle: ");
        String color = keyboard.nextLine().trim();

        for (Vehicle vehicle : dealership.getVehicleByColor(color)) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");
        }
    }

    public void processGetByMileageRequest() {
        System.out.print("Please enter the minimum mileage of the vehicle: ");
        int minMileage = Integer.parseInt(keyboard.nextLine().trim());

        System.out.print("Please enter the maximum mileage of the vehicle: ");
        int maxMileage = Integer.parseInt(keyboard.nextLine().trim());

        for (Vehicle vehicle : dealership.getvehicleByMileage(minMileage, maxMileage)) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");
        }
    }

    public void processGetByVehicleTypeRequest() {
        System.out.print("Please enter the vehicle type of the vehicle: ");
        String vehicleType = keyboard.nextLine().trim();

        for (Vehicle vehicle : dealership.getVehiclesByVehicleType(vehicleType)) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");
        }
    }

    public void processGetAllVehiclesRequest() {

        for (Vehicle vehicle : dealership.getAllVehicles()) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");

        }
    }

    public void processAddVehicleRequest() {

        while (true) {
            System.out.print("Please enter Vin (no more than 7): ");
            int vin = Integer.parseInt(keyboard.nextLine());
            if (vin > 99999) {
                System.out.println("Vin # is too big. please try again");
                continue;
            }

            System.out.print("Please enter year: ");
            int year = Integer.parseInt(keyboard.nextLine());

            System.out.print("Please enter make: ");
            String make = keyboard.nextLine();

            System.out.print("Please enter model: ");
            String model = keyboard.nextLine();

            System.out.print("Please enter vehicle type: ");
            String vehicleType = keyboard.nextLine();

            System.out.print("Please enter color: ");
            String color = keyboard.nextLine();

            System.out.print("Please enter odometer: ");
            int odometer = Integer.parseInt(keyboard.nextLine());

            System.out.print("Please enter price: ");
            double price = Double.parseDouble(keyboard.nextLine());

            Vehicle vehicle = new Vehicle(vin, year, odometer, make, model, vehicleType, color, price);

            dealership.addVehicle(vehicle);
            System.out.println("Vehicle Added!");

            //Ask user for additional vehicles
            System.out.println("Do you want to add another vehicle (Y or N)? ");
            String addAnother = keyboard.nextLine();

            if (addAnother.equalsIgnoreCase("y")) {
                return;
            } else if (addAnother.equalsIgnoreCase("n")) {
                dealerShipFileManager.addToInventory(vehicle);
                break;
            } else {
                System.out.println("Invalid command entered. please try again.");

            }
        }
    }

    public void processRemoveVehicleRequest() {
        System.out.print("Please enter the Vin you would like to remove: ");
        int userVin = Integer.parseInt(keyboard.nextLine());
        boolean vehicleFound = false;

        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (userVin == vehicle.getVin()) {
                vehicleFound = true;
                dealership.removeVehicle(vehicle);
                System.out.println("Vehicle has been removed!");
                dealerShipFileManager.removeFromInventory(vehicle);
                break;
            }
        }
        if (!vehicleFound) {
            System.out.println("No VIN matching " + userVin + " is found.");
        }
    }

    public void processContractRequest() {

        while (true) {
            System.out.print("are you wanting to lease or sale: ");
            String opt = keyboard.nextLine().trim();

            if (opt.equalsIgnoreCase("lease")) {
                int vin = 0, year, odometer;
                double price = 0;
                String make, model, vehicleType, color;

                String contactType = "LEASE";
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                System.out.print("please enter your name: ");
                String customerName = keyboard.nextLine().trim();
                System.out.print("please enter your email: ");
                String email = keyboard.nextLine().trim();
                System.out.print("enter vin of vehicle wanting to lease: ");
                int userVin = Integer.parseInt(keyboard.nextLine());
                boolean matchFound = false; // Initialize a flag for tracking if a match is found

                for (Vehicle vehicle : dealership.getAllVehicles()) {
                    if (userVin == vehicle.getVin()) {
                        price = vehicle.getPrice();
                        vin = vehicle.getVin();
                        matchFound = true; // Update flag to indicate a match
                        break; // Exit loop once a match is found
                    }
                }

                // Check if no match was found after the loop
                if (!matchFound) {
                    System.out.print("No VIN matching " + userVin + " is found.");
                }

                double expectedEndingValue = price * 0.50;
                double leasingFee = price * .07;
                double totalPrice = leasingFee + (price - expectedEndingValue);
                double monthlyPayment = totalPrice / 12;

                Lease lease = new Lease(date,
                        customerName,
                        vin,
                        email,
                        false,
                        totalPrice,
                        monthlyPayment,
                        expectedEndingValue,
                        leasingFee);

                dealership.addContract(lease);

                System.out.println("Lease contract completed and added successfully");

                System.out.print("Would you like to create another contract? (yes or no): ");
                String addOtherCont = keyboard.nextLine().trim();

                if (addOtherCont.equalsIgnoreCase("no")) {
                    contractDataManager.saveContract(lease, vin);
                    break;
                } else {
                    return;
                }

            } else if (opt.equalsIgnoreCase("sale")) {
                double price = 0;
                int vin = 0;

                String contactType = "SALE";
                String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                System.out.print("please enter your name: ");
                String customerName = keyboard.nextLine().trim();
                System.out.print("please enter your email: ");
                String email = keyboard.nextLine().trim();
                System.out.print("enter vin of vehicle wanting to lease: ");
                int userVin = Integer.parseInt(keyboard.nextLine());
                boolean matchFound = false; // Initialize a flag for tracking if a match is found

                for (Vehicle vehicle : dealership.getAllVehicles()) {
                    if (userVin == vehicle.getVin()) {
                        price = vehicle.getPrice();
                        vin = vehicle.getVin();
                        matchFound = true; // Update flag to indicate a match
                        break; // Exit loop once a match is found
                    }
                }

                // Check if no match was found after the loop
                if (!matchFound) {
                    System.out.println("No VIN matching " + userVin + " is found.");
                }

                double salesTax = price * .05;
                int recordingFee = 100;
                int proccessingFee = (price < 10000) ? 295 : 495;
                double totalPrice = (price * salesTax) + recordingFee + proccessingFee;

                System.out.print("Do you want to finance? yes or no: ");
                String userinput = keyboard.nextLine().trim();
                boolean financing = userinput.equalsIgnoreCase("yes");
                double monthlyPayment = totalPrice / 12;

                Sale sale = new Sale(date,
                        customerName,
                        vin,
                        email,
                        true,
                        totalPrice,
                        monthlyPayment,
                        salesTax,
                        recordingFee,
                        proccessingFee,
                        financing
                );
                dealership.addContract(sale);
                System.out.println("Sale contract completed and added successfully");

                System.out.print("Would you like to create another contract? (yes or no): ");
                String addOtherCont = keyboard.nextLine().trim();

                if (addOtherCont.equalsIgnoreCase("no")) {
                    contractDataManager.saveContract(sale, vin);
                    break;
                } else {
                    return;
                }
            } else {
                System.out.println("Wrong option. please try again");
            }
        }
    }

    public void contractScreen() {
        contractDataManager.getContracts(dealership);

        while (true) {
            System.out.print("""
                    1 - Create new contract
                    2 - View existing contracts
                    3 - Exit to home screen
                    
                    Please enter your selection:""");
            int opt = Integer.parseInt(keyboard.nextLine());

            switch (opt) {
                case 1:
                    processContractRequest();
                    break;
                case 2:
                    for (Contract contract : dealership.getContracts()) {
                        System.out.println(contract.getClass().getSimpleName());
                        System.out.println(contract.getCustomerName());
                        System.out.println(contract.getEmail());
                        System.out.println("Monthly Payment: " + contract.getMonthlyPayment());
                        System.out.println("--------");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option, try again");
            }

        }
    }
}


