package com.pluralsight;

public class Vehicle {
    private int vin,year,odometer;
    private String make,model,vehicleType,color;
    private double price;

    public Vehicle(int vin, int year, int odometer, String make, String model, String vehicleType, String color, double price) {
        this.vin = vin;
        this.year = year;
        this.odometer = odometer;
        this.make = make;
        this.model = model;
        this.vehicleType = vehicleType;
        this.color = color;
        this.price = price;
    }

    public int getVin() {
        return vin;
    }

    public int getYear() {
        return year;
    }

    public int getOdometer() {
        return odometer;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }
    public void displayVehicleInfo(){
        System.out.println("vehicle information");
        System.out.println( "vehicle vin: "+ getVin());
        System.out.printf("Make and model: %s %s \n",getMake(),getModel());
        System.out.println("Color:"+getColor());
        System.out.printf("odometer Reading:  %d\n", getOdometer());
        System.out.printf("price: %.2f \n",getPrice());
    }

    @Override
    public String toString() {
        return this.vin + "|" + this.year + "|" + this.make +"|"+ this.model +"|"+this.vehicleType+"|" +this.color+"|"+this.odometer+"|"+this.price;


    }
}
