package com.pluralsight;

import com.sun.source.tree.NewArrayTree;

import java.util.Scanner;

public class Main  {
    static final Scanner keyboard = new Scanner(System.in);
    public static void main(String[] args) {
        Dealership dealership = new Dealership();
        while (true) {
            System.out.printf("Welcome to %s's Auto Shop! \n", dealership.getName());
            UserInterface.display();
            int userInput = Integer.parseInt(keyboard.nextLine());
            System.out.println("-----------");
            switch (userInput) {
                case 1:
                    UserInterface.processgetByPriceRequest();
                    System.out.println("-----------");

                    break;
                case 2:
                    UserInterface.processGetBymakeModelrequest();
                    System.out.println("-----------");
                    break;
                case 3:
                    UserInterface.processgetByYearRequest();
                    System.out.println("-----------");
                    break;
                case 4:
                    UserInterface.processGetByColorRequest();
                    System.out.println("-----------");
                    break;
                case 5:
                    UserInterface.processGetByMileageRequest();
                    System.out.println("-----------");
                    break;
                case 6:
                    UserInterface.processGetByVehicleTypeRequest();
                    System.out.println("-----------");
                    break;
                case 7:
                    UserInterface.processGetAllVehiclesRequest();
                    System.out.println("-----------");
                    break;
                case 8:
                    UserInterface.processAddVehicleRequest();
                    System.out.println("-----------");
                    break;
                case 9:
                    UserInterface.processRemoveVehicleRequest();
                    System.out.println("-----------");
                    break;
                case 10:
                    UserInterface.contractScreen();
                    System.out.println("-----------");
                    break;
                case 99:
                    System.exit(0);
            }
        }

    }
}

