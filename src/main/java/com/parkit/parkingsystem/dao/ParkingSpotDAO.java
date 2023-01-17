package com.parkit.parkingsystem.dao;

import com.parkit.parkingsystem.config.DataBaseConfig;
import com.parkit.parkingsystem.constants.DBConstants;
import com.parkit.parkingsystem.constants.ParkingType;
import com.parkit.parkingsystem.model.ParkingSpot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ParkingSpotDAO {
    private static final Logger logger = LogManager.getLogger("ParkingSpotDAO");

    public DataBaseConfig dataBaseConfig = new DataBaseConfig();

    public int getNextAvailableSlot(ParkingType parkingType) {

        Connection con = null;


        int result = -1;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            con = dataBaseConfig.getConnection();
            preparedStatement = con.prepareStatement(DBConstants.GET_NEXT_PARKING_SPOT);
            preparedStatement.setString(1, parkingType.toString());
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }


        } catch (Exception ex) {
            logger.error("Error fetching next available slot", ex);
        } finally {
            dataBaseConfig.closeResultSet(rs);
            dataBaseConfig.closePreparedStatement(preparedStatement);
            dataBaseConfig.closeConnection(con);
        }
        return result;
    }

    public boolean updateParking(ParkingSpot parkingSpot) {
        //update the availability fo that parking slot
        Connection con = null;
        int updateRowCount = 0;
        PreparedStatement preparedStatement = null;
        try {
            con = dataBaseConfig.getConnection();
            preparedStatement = con.prepareStatement(DBConstants.UPDATE_PARKING_SPOT);
            preparedStatement.setBoolean(1, parkingSpot.isAvailable());
            preparedStatement.setInt(2, parkingSpot.getId());
            updateRowCount = preparedStatement.executeUpdate();


        } catch (Exception ex) {
            logger.error("Error updating parking info", ex);
        } finally {
            dataBaseConfig.closePreparedStatement(preparedStatement);
            dataBaseConfig.closeConnection(con);
            return (updateRowCount == 1);
        }
    }

}
