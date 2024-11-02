package com.pluralsight;

public abstract class Contract {
    protected String date,customerName,email;
    protected boolean vehicleSold;
    protected double totalPrice,monthlyPayment;

    public Contract(String date, String customerName, String email, boolean vehicleSold, double totalPrice, double monthlyPayment) {
        this.date = date;
        this.customerName = customerName;
        this.email = email;
        this.vehicleSold = vehicleSold;
        this.totalPrice = totalPrice;
        this.monthlyPayment = monthlyPayment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isVehicleSold() {
        return vehicleSold;
    }

    public void setVehicleSold(boolean vehicleSold) {
        this.vehicleSold = vehicleSold;
    }

    public abstract double getTotalPrice();
    public abstract double getMonthlyPayment();
}
