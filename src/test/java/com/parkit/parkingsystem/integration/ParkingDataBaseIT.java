package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;
import com.parkit.parkingsystem.model.Ticket;
import com.parkit.parkingsystem.service.ParkingService;
import com.parkit.parkingsystem.util.InputReaderUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ParkingDataBaseIT {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static TicketDAO ticketDAO;
    private static DataBasePrepareService dataBasePrepareService;



    @Mock
    private static InputReaderUtil inputReaderUtil;

    @BeforeAll
    private static void setUp() throws Exception {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();
    }

    @BeforeEach
    private void setUpPerTest() throws Exception {
        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }

    @AfterAll
    private static void tearDown() {
        dataBaseTestConfig.closeConnection(null);
    }


    @Test
    public void testParkingACar() {
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        assertEquals(2, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
        assertNotNull(ticket);
        assertNotNull(ticket.getInTime());
        assertEquals("ABCDEF", ticket.getVehicleRegNumber());
        assertFalse(parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR) == 1);
    }


    @Test
    public void ParkingLotExitCar() throws InterruptedException {
        testParkingACar();
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processExitingVehicle();
        Thread.sleep(1000);
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        //TODO: check that the fare generated and out time are populated correctly in the database
        assertNotNull(ticket);
        assertNotNull(ticket.getOutTime());
        assertEquals(0.0, ticket.getPrice());
        assertEquals(1, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
    }
    @Test
    void testParkingACarWithVehiculeRegNumberInTheDataBase() throws Exception {
        //create a new ticket with a new vehicleRegNumber a price, a parkingSpot, inTime and outTime and save it
        Ticket ticket = new Ticket();
        ParkingSpot parkingSpot = new ParkingSpot(2, ParkingType.CAR,false);
        ticket.setParkingSpot(parkingSpot);
        ticket.setVehicleRegNumber("123456");
        ticket.setPrice(2);
        ticket.setInTime(new Date());
        ticket.setOutTime(new Date());
        ticketDAO.saveTicket(ticket);

        when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("123456");
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        assertTrue(ticketDAO.recurentUser("123456"));







    }
}



