package com.pluralsight;

public class Sale extends Contract{

   private double salesTaxAmount;
    private int recordingFee,processingFee;
    private boolean wantToFinance;

    public Sale(String date, String customerName, String email, boolean vehicleSold, double totalPrice, double monthlyPayment, double salesTaxAmount, int recordingFee, int processingFee, boolean wantToFinance) {
        super(date, customerName, email, vehicleSold, totalPrice, monthlyPayment);
        this.salesTaxAmount = .05;
        this.recordingFee = 100;
        this.processingFee = processingFee;
        this.wantToFinance = wantToFinance;
    }

    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(double salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
    }

    public int getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(int recordingFee) {
        this.recordingFee = recordingFee;
    }

    public int getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(int processingFee) {
        this.processingFee = processingFee;
    }

    public boolean isWantToFinance() {
        return wantToFinance;
    }

    public void setWantToFinance(boolean wantToFinance) {
        this.wantToFinance = wantToFinance;
    }

    @Override
    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public void getMonthlyPayment() {
        if(this.wantToFinance && totalPrice >= 10000) {
            this.monthlyPayment = (totalPrice * 0.003542 * Math.pow(1 + 0.003542, 48)) / (Math.pow(1 + 0.003542, 48) - 1);

        }else {
            this.monthlyPayment = (totalPrice * 0.04375 * Math.pow(1 + 0.04375, 24)) / (Math.pow(1 + 0.04375, 25) - 1);

        }

    }
}
