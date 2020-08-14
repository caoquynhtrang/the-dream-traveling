/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.traveltour;

import java.io.Serializable;
import java.sql.Date;
import java.text.DecimalFormat;
import trangcq.status.StatusDTO;

/**
 *
 * @author USER
 */
public class TravelTourDTO implements Serializable {

    private String place, tourName, imageLink;
    private int tourId, quota, statusId;
    private float price;
    Date fromDate, toDate, importDate;
    private StatusDTO statusDTO;

    public StatusDTO getStatusDTO() {
        return statusDTO;
    }
    

    public TravelTourDTO() {
    }

    public TravelTourDTO(String place, String tourName, String imageLink, int quota, float price, Date fromDate, Date toDate) {
        this.place = place;
        this.tourName = tourName;
        this.imageLink = imageLink;
        this.quota = quota;
        this.price = price;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    
    
    public TravelTourDTO(String place, String tourName, String imageLink, int quota, int statusId, float price, Date fromDate, Date toDate, Date importDate) {
        this.place = place;
        this.tourName = tourName;
        this.imageLink = imageLink;
        this.quota = quota;
        this.statusId = statusId;
        this.price = price;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.importDate = importDate;
    }
    

    public TravelTourDTO(String tourName, String imageLink, int tourId, float price, Date fromDate, Date toDate) {
        this.tourName = tourName;
        this.imageLink = imageLink;
        this.tourId = tourId;
        this.price = price;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getPriceDisplay(int tourId) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(price);
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

}
