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
        return new ParkingSpot(parkingSpot.getId(), parkingSpot.getParkingType(), parkingSpot.isAvailable());
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
        if (inTime != null) {
            return new Date(inTime.getTime());
        } else {
            return null;
        }
    }

    public void setInTime(Date inTime) {
        if (inTime != null) {
            this.inTime = new Date(inTime.getTime());
        } else {
            this.inTime = null;
        }
    }

    public Date getOutTime() {
       if (outTime != null) {
            return new Date(outTime.getTime());
        } else {
            return null;
        }
    }

    public void setOutTime(Date outTime) {
        if (outTime != null) {
            Date date = new Date(outTime.getTime());
            this.outTime = date;
        } else {
            this.outTime = null;
        }
    }


}
