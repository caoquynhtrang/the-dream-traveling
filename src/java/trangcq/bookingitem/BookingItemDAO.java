/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.bookingitem;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import trangcq.conn.MyConnection;

/**
 *
 * @author USER
 */
public class BookingItemDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public BookingItemDAO() {
    }

    public void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }
    
    public int countTotalBookedTour(int travelTour) throws SQLException, NamingException{
        String sql = "SELECT COUNT(bi.Id) as TotalBooked FROM BookingItem bi "
                + "WHERE bi.TravelTourId = ? ";
        int totalBooked = 0;
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, travelTour);
            
            rs = preStm.executeQuery();
            if(rs.next()){
                totalBooked = rs.getInt("TotalBooked");
            }
        } finally{
            closeConnection();
        }
        return totalBooked;
    }

    public boolean insertBookingItem(BookingItemDTO dto) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "INSERT INTO [dbo].[BookingItem] "
                    + "           ([BookingId] "
                    + "           ,[TravelTourId] "
                    + "           ,[Amount]) "
                    + "     VALUES(?, ?, ?)" ;
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, dto.getBookingId());
            preStm.setInt(2, dto.getTravelTourId());
            preStm.setInt(3, dto.getAmount());
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }
}
