/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.traveltour;

import java.io.Serializable;

/**
 *
 * @author USER
 */
public class TravelTourInsertErr implements Serializable {

    private String tourNameErr;
    private String placeErr;
    private String fromDateErr;
    private String toDateErr;
    private String quotaErr;
    private String priceErr;
    private String imageErr;
    private String toDateGreaterErr;

    public TravelTourInsertErr() {
    }

    public TravelTourInsertErr(String tourNameErr, String placeErr, String fromDateErr, String toDateErr, String quotaErr, String priceErr, String imageErr, String toDateGreaterErr) {
        this.tourNameErr = tourNameErr;
        this.placeErr = placeErr;
        this.fromDateErr = fromDateErr;
        this.toDateErr = toDateErr;
        this.quotaErr = quotaErr;
        this.priceErr = priceErr;
        this.imageErr = imageErr;
        this.toDateGreaterErr = toDateGreaterErr;
    }

    public String getToDateGreaterErr() {
        return toDateGreaterErr;
    }

    public void setToDateGreaterErr(String toDateGreaterThanFromDate) {
        this.toDateGreaterErr = toDateGreaterThanFromDate;
    }
    
    

    public String getTourNameErr() {
        return tourNameErr;
    }

    public void setTourNameErr(String tourNameErr) {
        this.tourNameErr = tourNameErr;
    }

    public String getPlaceErr() {
        return placeErr;
    }

    public void setPlaceErr(String placeErr) {
        this.placeErr = placeErr;
    }

    public String getFromDateErr() {
        return fromDateErr;
    }

    public void setFromDateErr(String fromDateErr) {
        this.fromDateErr = fromDateErr;
    }

    public String getToDateErr() {
        return toDateErr;
    }

    public void setToDateErr(String toDateErr) {
        this.toDateErr = toDateErr;
    }

    public String getQuotaErr() {
        return quotaErr;
    }

    public void setQuotaErr(String quotaErr) {
        this.quotaErr = quotaErr;
    }

    public String getPriceErr() {
        return priceErr;
    }

    public void setPriceErr(String priceErr) {
        this.priceErr = priceErr;
    }

    public String getImageErr() {
        return imageErr;
    }

    public void setImageErr(String imageErr) {
        this.imageErr = imageErr;
    }

}
