package com.pluralsight;

import java.util.Scanner;

public class Main  {
    static final Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("We have a problem, please provide a username and password for database");
            System.exit(1);
        }

        Dealership dealership = new Dealership();

        UserInterface userInterface = new UserInterface(args);

        while (true) {
            System.out.printf("Welcome to %s's Auto Shop! \n", dealership.getName());
            UserInterface.display();
            int userInput = Integer.parseInt(keyboard.nextLine());
            System.out.println("-----------");

            switch (userInput) {
                case 1:
                    userInterface.processGetByPriceRequest();
                    System.out.println("-----------");
                    break;
                case 2:
                    userInterface.processGetByMakeModelRequest();
                    System.out.println("-----------");
                    break;
                case 3:
                    userInterface.processGetByYearRequest();
                    System.out.println("-----------");
                    break;
                case 4:
                    userInterface.processGetByColorRequest();
                    System.out.println("-----------");
                    break;
                case 5:
                    userInterface.processGetByMileageRequest();
                    System.out.println("-----------");
                    break;
                case 6:
                    userInterface.processGetByVehicleTypeRequest();
                    System.out.println("-----------");
                    break;
                case 7:
                    userInterface.processGetAllVehiclesRequest();
                    System.out.println("-----------");
                    break;
                case 8:
                    userInterface.processAddVehicleRequest();
                    System.out.println("-----------");
                    break;
                case 9:
                    userInterface.processRemoveVehicleRequest();
                    System.out.println("-----------");
                    break;
                case 10:
                    userInterface.contractScreen();
                    System.out.println("-----------");
                    break;
                case 99:
                    System.out.println("Thank you for visiting!");
                    System.exit(0);
            }
        }

    }
}

