package com.pluralsight;

public class Lease extends Contract{

   private double expectedEndingValue, leaseFee;
   private int VIN;

    public Lease(String date, String customerName, int VIN, String email, boolean vehicleSold, double totalPrice, double monthlyPayment, double expectedEndingValue, double leaseFee) {
        super(date, customerName,email, vehicleSold, totalPrice, monthlyPayment);
        this.expectedEndingValue = expectedEndingValue;
        this.leaseFee = leaseFee;
        this.VIN = VIN;
    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public void setExpectedEndingValue(double expectedEndingValue) {
        this.expectedEndingValue = expectedEndingValue;
    }

    public double getLeaseFee() {
        return leaseFee;
    }

    public void setLeaseFee(double leaseFee) {
        this.leaseFee = leaseFee;
    }


    @Override
    public double getTotalPrice() {

        return totalPrice;

    }

    @Override
    public double getMonthlyPayment() {

        return monthlyPayment;

    }
}
