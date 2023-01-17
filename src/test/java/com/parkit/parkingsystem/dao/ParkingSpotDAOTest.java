package com.parkit.parkingsystem.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.integration.config.DataBaseTestConfig;
import com.parkit.parkingsystem.integration.service.DataBasePrepareService;
import com.parkit.parkingsystem.model.ParkingSpot;


import static org.junit.jupiter.api.Assertions.*;

class ParkingSpotDAOTest {

    private static DataBaseTestConfig dataBaseTestConfig = new DataBaseTestConfig();
    private static ParkingSpotDAO parkingSpotDAO;
    private static ParkingSpot parkingSpot;

    @BeforeEach
    void setUp() {
        parkingSpotDAO = new ParkingSpotDAO();
        parkingSpotDAO.dataBaseConfig = dataBaseTestConfig;

    }

    @AfterEach
    void tearDown() {
        dataBaseTestConfig.closeConnection(null);
    }

    @Test
    void getNextAvailableSlot() {

         parkingSpot = new ParkingSpot(1, ParkingType.CAR,false);
         int result = parkingSpotDAO.getNextAvailableSlot(parkingSpot.getParkingType());
         assertEquals(2, result);
    }

    @Test
    void updateParking() {
        parkingSpot = new ParkingSpot(2, ParkingType.CAR, true);
        parkingSpotDAO.updateParking(parkingSpot);
        assertTrue(parkingSpot.isAvailable());
    }

}