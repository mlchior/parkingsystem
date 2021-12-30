package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        //convertir l'heure des date en milliseconde
   /* Date inTime = ticket.getInTime();
        long inTimeStamp = ticket.getInTime().getTime();

    Date OutTime = ticket.getOutTime();
        long outTimeStamp = ticket.getOutTime().getTime();

        long durationTimeStamp  = outTimeStamp - inTimeStamp;*/


        long x = ChronoUnit.HOURS.MINUTES.between(ticket.getInTime().toInstant(), ticket.getOutTime().toInstant());
        int duration = (int) x;
       // if (duration < 1) { ChronoUnit.MINUTES.between(ticket.getInTime().toInstant(), ticket.getOutTime().toInstant());
        //ajouter le cas particulier sans qure ca casse les autres test
        //if duration < 1 alors  ChronoUnit.Minute


        // int inHour = ticket.getInTime().getHours();
        //int outHour = ticket.getOutTime().getHours();

        //: Some tests are failing here. Need to check if this logic is correct
       // changer la calcul de date si plus de 24h
      //  int duration = outHour - inHour;
        // if  dayDateIn != dayDateOut
        //  x = inTimeStamp - outTimeStamp
        // x = 456454644
        //


        switch (ticket.getParkingSpot().getParkingType()){
            case CAR: {
                ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
};