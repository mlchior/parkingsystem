package com.parkit.parkingsystem.integration;

import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.dao.ParkingSpotDAO;
import com.parkit.parkingsystem.dao.TicketDAO;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
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
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private static void setUp() throws Exception{

        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;
        ticketDAO = new TicketDAO();
        ticketDAO.dataBaseConfig = dataBaseTestConfig;
        dataBasePrepareService = new DataBasePrepareService();

        }

    @BeforeEach
    private void setUpPerTest() throws Exception {
      //  when(inputReaderUtil.readSelection()).thenReturn(1);
        when(inputReaderUtil.readVehicleRegistrationNumber()).thenReturn("ABCDEF");
        dataBasePrepareService.clearDataBaseEntries();
    }


    @AfterAll
    private static void tearDown(){

    }


    @Test
    public void testParkingACar(){

        when(inputReaderUtil.readSelection()).thenReturn(1);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        Ticket ticket1 =  ticketDAO.getTicket("ABCDEF");
        Ticket ticket2 = ticketDAO.getTicket("ABCDEF");
        assertEquals("ABCDEF",ticket1.getVehicleRegNumber());
        assertEquals("ABCDEF",ticket2.getVehicleRegNumber());
        assertEquals(ticket1.getVehicleRegNumber(),ticket2.getVehicleRegNumber());
        assertEquals(ticket1.getId(),ticket2.getId());
        assertEquals(ticket1.getInTime(),ticket2.getInTime());
        assertEquals(ticket1.getParkingSpot(),ticket2.getParkingSpot());
        assertEquals(2, parkingSpotDAO.getNextAvailableSlot(ParkingType.CAR));
        //: check that a ticket is actualy saved in DB and Parking table is updated with availability
    }// regarder mockito fuctin simuler BD
    @Test
    public void testParkingABike(){
        when(inputReaderUtil.readSelection()).thenReturn(2);
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        parkingService.processIncomingVehicle();
        ticketDAO.getTicket("ABCDEF");
        assertEquals(5, parkingSpotDAO.getNextAvailableSlot(ParkingType.BIKE));
        //: check that a ticket is actualy saved in DB and Parking table is updated with availability
    }// regarder mockito fuctin simuler BD


    @Test
    public void testParkingLotExitCar(){
// fonction createticketindatabase qui sert a implemanter les donnée avant le test genre heure d'arrivé = 1- que l'heure de sorti
        testParkingACar();
        ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
        Ticket ticket = ticketDAO.getTicket("ABCDEF");
        Date dateIn = new Date(System.currentTimeMillis()-3600*1000);
        //updateticket
        parkingService.processExitingVehicle();

        //O: check that the fare generated and out time are populated correctly in the database

        assertNotNull(ticket.getOutTime());

        Date dateOut = new Date(System.currentTimeMillis());
        // getT
        assertNotNull(ticket.getPrice());


//creer un ticket en base de donnée
      //  processexisting vehicule est qu'il va mettre a jour mon ticket'
    }
 @Test
    public void testParkingLotExitBike(){
        testParkingABike();
     ParkingService parkingService = new ParkingService(inputReaderUtil, parkingSpotDAO, ticketDAO);
     parkingService.processExitingVehicle();
     Ticket ticket = ticketDAO.getTicket("ABCDEF");
     Date dateIn = new Date(System.currentTimeMillis()-3600*1000);
     Date dateOut = new Date(System.currentTimeMillis());
     ticket.setInTime(dateIn);
     ticket.setOutTime(dateOut);
     assertNotNull(ticket.getPrice());
     assertNotNull(ticket.getOutTime());



 }

 // creer une fonction pour changer le dateIn
}
