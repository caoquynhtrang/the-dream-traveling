/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.traveltour;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import trangcq.conn.MyConnection;

/**
 *
 * @author USER
 */
public class TravelTourDAO implements Serializable {

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    public TravelTourDAO() {
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

    
    
    public int countTotalTravelTour(String place, Date fromDatetime, Date toDatetime, float toPrice, float fromPrice) throws SQLException, NamingException {

        int count = 2;
        int countPage = 0;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        try {
            String sql = "SELECT COUNT(TourId) as totalRows from TravelTour T "
                    + "WHERE T.StatusId = 1 "
                    + " AND T.FromDate > ? "
                    + "	And T.Quota > (SELECT COUNT(BI.Amount) AS Amount "
                    + "					From [dbo].[BookingItem] BI "
                    + "					Where BI.TravelTourId = T.TourId) ";
            if (place != null && !place.isEmpty()) {
                sql += "And T.Place like ? ";
            }
            if (fromDatetime != null) {
                sql += "And T.FromDate >= ? ";
            }
            if (toDatetime != null) {
                sql += "AND T.ToDate <= ? ";
            }
            if (fromPrice >= 0) {
                sql += "AND T.Price >= ? ";
            }
            if (toPrice > 0 && toPrice >= fromPrice) {
                sql += "AND T.Price <= ? ";
            }
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setTimestamp(1, now);
            if (place != null && !place.isEmpty()) {
                preStm.setString(count, "%" + place + "%");
                count++;
            }
            if (fromDatetime != null) {
                preStm.setDate(count, fromDatetime);
                count++;
            }
            if (toDatetime != null) {
                preStm.setDate(count, toDatetime);
                count++;
            }
            if (fromPrice > 0) {
                preStm.setFloat(count, fromPrice);
                count++;
            }
            if (toPrice > 0 && toPrice >= fromPrice) {
                preStm.setFloat(count, toPrice);
                count++;
            }

            rs = preStm.executeQuery();
            if (rs.next()) {
                countPage = rs.getInt("totalRows");
            }
        } finally {
            closeConnection();
        }
        return countPage;
    }

    public List<TravelTourDTO> searchTourPaging(String place, Date fromDatetime, Date toDatetime, float toPrice, float fromPrice) throws SQLException, NamingException {
        return searchTourPaging(place, fromDatetime, toDatetime, toPrice, fromPrice, 1);
    }

    public List<TravelTourDTO> searchTourPaging(String place, Date fromDatetime, Date toDatetime, float toPrice, float fromPrice, int pageNumber) throws SQLException, NamingException {
        List<TravelTourDTO> result = new ArrayList<>();
        int pageSize = 20;
        int count = 2;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        try {
            String sql = "SELECT T.TourId, T.TourName, T.Price, T.FromDate, T.ToDate, T.ImageLink "
                    + "From [dbo].[TravelTour] T "
                    + "WHERE T.StatusId = 1 "
                    + " AND T.FromDate > ? "
                    + "	And T.Quota > (SELECT COUNT(BI.Amount) AS Amount "
                    + "					From [dbo].[BookingItem] BI "
                    + "					Where BI.TravelTourId = T.TourId) ";

            if (place != null && !place.isEmpty()) {
                sql += "And T.Place like ? ";
            }
            if (fromDatetime != null) {
                sql += "And T.FromDate >= ? ";
            }
            if (toDatetime != null) {
                sql += "AND T.ToDate <= ? ";
            }
            if (fromPrice >= 0) {
                sql += "AND T.Price >= ? ";
            }
            if (toPrice > 0 && toPrice >= fromPrice) {
                sql += "AND T.Price <= ? ";
            }
            sql += "ORDER BY FromDate "
                    + "OFFSET ? ROWS "
                    + "FETCH NEXT ? ROWS ONLY";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setTimestamp(1, now);
            if (place != null && !place.isEmpty()) {
                preStm.setString(count, "%" + place + "%");
                count++;
            }
            if (fromDatetime != null) {
                preStm.setDate(count, fromDatetime);
                count++;
            }
            if (toDatetime != null) {
                preStm.setDate(count, toDatetime);
                count++;
            }
            if (fromPrice > 0) {
                preStm.setFloat(count, fromPrice);
                count++;
            }
            if (toPrice > 0 && toPrice >= fromPrice) {
                preStm.setFloat(count, toPrice);
                count++;
            }
            preStm.setInt(count, pageSize * (pageNumber - 1));
            preStm.setInt(count + 1, pageSize);
            rs = preStm.executeQuery();
            while (rs.next()) {
                int tourId = rs.getInt("TourId");
                String tourName = rs.getString("TourName");
                float price = rs.getFloat("Price");
                Date fromDate = rs.getDate("FromDate");
                Date toDate = rs.getDate("ToDate");
                String imageLink = rs.getString("ImageLink");
                TravelTourDTO dto = new TravelTourDTO(tourName, imageLink, tourId, price, fromDate, toDate);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public TravelTourDTO getTravelTour(int tourId) throws SQLException, NamingException {
        TravelTourDTO result = null;
        try {
            String sql = "SELECT [TourName] "
                    + "      ,[TourId] "
                    + "      ,[FromDate] "
                    + "      ,[ToDate] "
                    + "      ,[Price] "
                    + "      ,[ImageLink] "
                    + "  FROM [dbo].[TravelTour] "
                    + "  Where TourId = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, tourId);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String tourName = rs.getString("TourName");
                Date fromDate = rs.getDate("FromDate");
                Date toDate = rs.getDate("ToDate");
                float price = rs.getFloat("Price");
                String imageLink = rs.getString("ImageLink");
                result = new TravelTourDTO(tourName, imageLink, tourId, price, fromDate, toDate);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public TravelTourDTO getTravelTourWithQuota(int tourId) throws SQLException, NamingException {
        TravelTourDTO result = null;
        try {
            String sql = "SELECT [TourName] "
                    + "      ,[TourId] "
                    + "      ,[FromDate] "
                    + "      ,[ToDate] "
                    + "      ,[Price] "
                    + "      ,[Quota] "
                    + "      ,[ImageLink] "
                    + "  FROM [dbo].[TravelTour] "
                    + "  Where TourId = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, tourId);
            rs = preStm.executeQuery();
            if (rs.next()) {
                String tourName = rs.getString("TourName");
                Date fromDate = rs.getDate("FromDate");
                Date toDate = rs.getDate("ToDate");
                float price = rs.getFloat("Price");
                String imageLink = rs.getString("ImageLink");
                int quota = rs.getInt("Quota");
                result = new TravelTourDTO(tourName, imageLink, tourId, price, fromDate, toDate);
                result.setQuota(quota);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public int getTourQuota(int tourId) throws SQLException, NamingException {
        int res = 0;
        try {
            String sql = "SELECT Quota "
                    + "  FROM [dbo].[TravelTour] "
                    + "  Where TourId = ? ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, tourId);
            rs = preStm.executeQuery();
            if (rs.next()) {
                res = rs.getInt("Quota");
            }
        } finally {
            closeConnection();
        }
        return res;
    }

    public boolean insertTourTravel(TravelTourDTO dto) throws SQLException, NamingException {
        boolean result = false;
        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        try {
            String sql = "INSERT INTO [dbo].[TravelTour] "
                    + "           ([Place] "
                    + "           ,[TourName] "
                    + "           ,[FromDate] "
                    + "           ,[ToDate] "
                    + "           ,[Price] "
                    + "           ,[Quota] "
                    + "           ,[ImageLink] "
                    + "           ,[ImportedDate] "
                    + "           ,[StatusId]) "
                    + "     VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            conn = MyConnection.getMyConnection();
            preStm = conn.prepareStatement(sql);
            preStm.setString(1, dto.getPlace());
            preStm.setString(2, dto.getTourName());
            preStm.setDate(3, dto.getFromDate());
            preStm.setDate(4, dto.getToDate());
            preStm.setFloat(5, dto.getPrice());
            preStm.setInt(6, dto.getQuota());
            preStm.setString(7, dto.getImageLink());
            preStm.setTimestamp(8, currentDate);
            preStm.setInt(9, 1);
            result = preStm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }
}
