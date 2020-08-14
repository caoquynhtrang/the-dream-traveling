/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.booking;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author USER
 */
public class BookingDTO implements Serializable{
    private int id, userId, discountId;
    private Timestamp importedDate;

    public BookingDTO() {
    }

    public BookingDTO(int id, int userId, int discountId, Timestamp importedDate) {
        this.id = id;
        this.userId = userId;
        this.discountId = discountId;
        this.importedDate = importedDate;
    }

    public BookingDTO(int userId, int discountId, Timestamp importedDate) {
        this.userId = userId;
        this.discountId = discountId;
        this.importedDate = importedDate;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public Timestamp getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(Timestamp importedDate) {
        this.importedDate = importedDate;
    }
    
    
    
}
