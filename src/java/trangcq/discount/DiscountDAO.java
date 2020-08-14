/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.discount;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trangcq.conn.MyConnection;

/**
 *
 * @author USER
 */
public class DiscountDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public DiscountDAO() {
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

    public List<DiscountDTO> getDiscountCode(int userId) throws SQLException, NamingException {
        Date currentDate = new Date(System.currentTimeMillis());
        List<DiscountDTO> result = new ArrayList<>();
        try {
            String sql = "Select Id, Code "
                    + "From Discount WHERE Id NOT IN (Select D.Id "
                    + "                               From Booking B Join Discount D On B.DiscountId = D.Id "
                    + "                               Where B.UserId = ?) "
                    + "     And [ExpiredDate] >= ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, userId);
            preStm.setDate(2, currentDate);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String code = rs.getString("Code");
                DiscountDTO dto = new DiscountDTO(id, code);
                result.add(dto);
            }

        } finally {
            closeConnection();
        }
        return result;
    }

    
    public DiscountDTO getDiscountByID(int id) throws SQLException, NamingException {
        DiscountDTO result = null;
        try {
            String sql = "SELECT [Code] "
                    + "      ,[ExpiredDate] "
                    + "      ,[Value] "
                    + "  FROM [dbo].[Discount] "
                    + "  Where ID = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, id);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String code = rs.getString("Code");
                Date expiredDate = rs.getDate("ExpiredDate");
                float value = rs.getFloat("Value");
                result = new DiscountDTO(id, code, expiredDate, value);
                
            }
        } finally {
            closeConnection();
        }
        return result;
    }

}
