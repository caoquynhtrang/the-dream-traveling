/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.bookingitem;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class BookingItemDTO implements Serializable{
    private int id, bookingId, travelTourId, amount;

    public BookingItemDTO() {
    }

    public BookingItemDTO(int id, int bookingId, int travelTourId, int amount) {
        this.id = id;
        this.bookingId = bookingId;
        this.travelTourId = travelTourId;
        this.amount = amount;
    }

    public BookingItemDTO(int bookingId, int travelTourId, int amount) {
        this.bookingId = bookingId;
        this.travelTourId = travelTourId;
        this.amount = amount;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getTravelTourId() {
        return travelTourId;
    }

    public void setTravelTourId(int travelTourId) {
        this.travelTourId = travelTourId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
}
