package com.parkit.parkingsystem.dao;

import com.mysql.cj.jdbc.CallableStatement;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TicketDAOTest {
    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;
    private static Ticket ticket;


    @BeforeEach
     void setUp() {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
        ticket = new Ticket();

    }

    @AfterEach
    void tearDown() {
        dataBaseTestConfig.closeConnection(null);
    }

    @Test
    void saveTicket() {

        ParkingSpot parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("ABCDEF");
//when
       ticketDAO.saveTicket(ticket);
     //  if (ticketDAO.saveTicket(ticket) == true){
    //    }
//then
       Ticket  ticketTest = ticketDAO.getTicket("ABCDEF") ;
       assertEquals(ticket.getVehicleRegNumber(),ticketTest.getVehicleRegNumber());

       assertEquals(ticket.getParkingSpot(), ticketTest.getParkingSpot());

    }
    @Test
    void getTicket() {
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        assertNotNull(ticket);
        assertEquals("ABCDEF", ticket.getVehicleRegNumber());

    }
    @Test
    void getTicketThrowExecption() {
        Ticket ticket = ticketDAO.getTicket("123456");
        assertNull(ticket);

    }


    @Test
    void updateTicket() {
        Ticket ticket =  ticketDAO.getTicket("ABCDEF");ticket.setPrice(2);
        ticketDAO.updateTicket(ticket);
        Ticket ticketTest = ticketDAO.getTicket("ABCDEF");
        assertEquals(ticket.getParkingSpot() , ticketTest.getParkingSpot());
    }

}