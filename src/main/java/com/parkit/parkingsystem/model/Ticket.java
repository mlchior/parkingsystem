package com.parkit.parkingsystem.model;
import java.util.Date;

public class Ticket {


    private boolean member;
    private int id;
    private ParkingSpot parkingSpot;
    private String vehicleRegNumber;
    private double price;
    private Date inTime;
    private Date outTime;



    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot) {
        ParkingSpot parkingSpot1 = new ParkingSpot();
        parkingSpot1.setId(parkingSpot.getId());
        parkingSpot1.setParkingType(parkingSpot.getParkingType());
        parkingSpot1.setAvailable(parkingSpot.isAvailable());
        this.parkingSpot = parkingSpot1;
    }

    public String getVehicleRegNumber() {
        return vehicleRegNumber;
    }

    public void setVehicleRegNumber(String vehicleRegNumber) {
        this.vehicleRegNumber = vehicleRegNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getInTime() {
        return new Date(inTime.getTime());
    }

    public void setInTime(Date inTime) {
        Date date = new Date(inTime.getTime());
        this.inTime = date;
    }

    public Date getOutTime() {
        return new Date(outTime.getTime());
    }

    public void setOutTime(Date outTime) {
        Date date = new Date(outTime.getTime());
        this.outTime = date;
    }


}
