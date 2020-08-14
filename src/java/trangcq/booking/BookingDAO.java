/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.booking;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import javax.naming.NamingException;
import trangcq.conn.MyConnection;

/**
 *
 * @author USER
 */
public class BookingDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public BookingDAO() {
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

    public boolean checkDiscount(int discountId, int userId) throws SQLException, NamingException {
        boolean check = true;
        String sql = "SELECT Id"
                + "  FROM Booking"
                + "  WHERE DiscountId = ? AND UserId = ?";
        try {
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, discountId);
            preStm.setInt(2, userId);
            
            rs = preStm.executeQuery();
            if(rs.next()){
                check = false;
            }
            
        } finally {
            closeConnection();
        }
        return check;
    }

    public int insertBookingTour(BookingDTO dto) throws SQLException, NamingException {
        int id = -1;
        try {
            String sql = "INSERT INTO [dbo].[Booking] "
                    + "           ([ImportedDate] "
                    + "           ,[UserId] "
                    + "           ,[DiscountId]) "
                    + "  OUTPUT Inserted.Id "
                    + "  VALUES(?, ?, ?)";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preStm.setInt(2, dto.getUserId());
            if (dto.getDiscountId() == -1) {
                preStm.setNull(3, Types.INTEGER);
            } else {
                preStm.setInt(3, dto.getDiscountId());
            }

            rs = preStm.executeQuery();
            if (rs.next()) {
                id = rs.getInt("Id");
            }
        } finally {
            closeConnection();
        }
        return id;
    }

}
