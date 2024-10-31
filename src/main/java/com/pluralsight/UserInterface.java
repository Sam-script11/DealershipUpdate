package com.pluralsight;


import java.util.Scanner;

public class UserInterface {
    static final Scanner keyboard = new Scanner(System.in);
    public static Dealership dealership = DealerShipFileManager.getDealership();

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
                10- Contract Screen
                99 - Quit
                Please enter your selection:""");
    }

    public static void processgetByPriceRequest() {
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

    public static void processGetBymakeModelrequest() {
        System.out.print("Please enter the make of the vehicle: ");
        String make = keyboard.nextLine().trim();

        System.out.print("Please enter the model of the vehicle: ");
        String model = keyboard.nextLine().trim();

        for (Vehicle vehicle : dealership.getVehicleByMakeMode(make, model)) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");

        }
    }

    public static void processgetByYearRequest() {
        System.out.print("Please enter the minimum year of the vehicle: ");
        int minYear = Integer.parseInt(keyboard.nextLine().trim());

        System.out.print("Please enter the maximum year of the vehicle: ");
        int maxYear = Integer.parseInt(keyboard.nextLine().trim());

        for (Vehicle vehicle : dealership.getVehiclesByYear(minYear, maxYear)) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");
        }
    }

    public static void processGetByColorRequest() {
        System.out.print("Please enter the color of the vehicle: ");
        String color = keyboard.nextLine().trim();

        for (Vehicle vehicle : dealership.getVehicleByColor(color)) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");
        }
    }

    public static void processGetByMileageRequest() {
        System.out.print("Please enter the minimum mileage of the vehicle: ");
        int minMileage = Integer.parseInt(keyboard.nextLine().trim());

        System.out.print("Please enter the maximum mileage of the vehicle: ");
        int maxMileage = Integer.parseInt(keyboard.nextLine().trim());

        for (Vehicle vehicle : dealership.getvehicleByMileage(minMileage, maxMileage)) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");
        }
    }

    public static void processGetByVehicleTypeRequest() {
        System.out.print("Please enter the vehicle type of the vehicle: ");
        String vehicleType = keyboard.nextLine().trim();

        for (Vehicle vehicle : dealership.getVehiclesByVehicleType(vehicleType)) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");
        }
    }

    public static void processGetAllVehiclesRequest() {

        for (Vehicle vehicle : dealership.getAllVehicles()) {
            vehicle.displayVehicleInfo();
            System.out.println("-----------");

        }
    }

    public static void processAddVehicleRequest() {

        while (true) {
            System.out.print("Please enter Vin (no more than 5): ");
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


            dealership.addVehicle(new Vehicle(vin,year,odometer,make,model,vehicleType,color,price));
            System.out.println("Vehicle Added!");

            //Ask user for additional vehicles
            System.out.println("Do you want to add another vehicle (Y or N)? ");
            String addAnother = keyboard.nextLine();
            if (addAnother.equalsIgnoreCase("y")) {
                continue;
            } else if (addAnother.equalsIgnoreCase("n")) {
                DealerShipFileManager.saveInventory(new Dealership());
                break;
            } else {
                System.out.println("Invalid command entered. please try again.");

            }
        }
    }

    public static void processRemoveVehicleRequest() {
        System.out.println("Please enter the Vin you would like to remove: ");
        int userVin = Integer.parseInt(keyboard.nextLine());
        boolean vehicleFound = false;

        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (userVin == vehicle.getVin()) {
                vehicleFound = true;
                dealership.removeVehicle(vehicle);
                System.out.println("Vehicle has been removed!");
                DealerShipFileManager.saveInventory(new Dealership());
                break;
            }
        }

        if (!vehicleFound) {
            System.out.println("No VIN matching " + userVin + " is found.");
        }
    }

    public static void processContractScreen(){

        ContractDataManager.getContracts(dealership);

        for(Contract contract: dealership.getContracts()){
            System.out.println(contract.getCustomerName());
        }
        }

    }

