/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.cart;

import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import trangcq.discount.DiscountDTO;
import trangcq.traveltour.TravelTourDAO;
import trangcq.traveltour.TravelTourDTO;

/**
 *
 * @author USER
 */
public class CartObject implements Serializable {

    private TravelTourDTO travelTourDTO;
    public Map<Integer, Integer> items;
    public Map<Integer, TravelTourDTO> travelTour;
    public DiscountDTO discoutDTO;

    
    public long getDiscountPercent(){
        if(discoutDTO == null){
            return 0;
        }
        return (long) discoutDTO.getValue();
    }
    public DiscountDTO getDiscountDTO() {
        return discoutDTO;
    }
    

    public void setDiscoutDTO(DiscountDTO discoutDTO) {
        this.discoutDTO = discoutDTO;
    }
    
    

    public Map<Integer, Integer> getItems() {
        return items;
    }

    public Map<Integer, TravelTourDTO> getTravelTour() {
        return travelTour;
    }

    public void addItemToCart(int tourId) throws SQLException, NamingException {
        if (tourId == 0) {
            return;
        }
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        int amount = 1;
        if (this.items.containsKey(tourId)) {
            amount = this.items.get(tourId) + 1;
        }

        this.items.put(tourId, amount);

        TravelTourDAO dao = new TravelTourDAO();
        travelTourDTO = dao.getTravelTourWithQuota(tourId);

        if (travelTour == null) {
            travelTour = new HashMap<>();
        }
        travelTour.put(tourId, travelTourDTO);

    }

    public boolean updateItem(int tourId, int amount) {
        if (amount <= 0) {
            return false;
        }

        if (items.containsKey(tourId)) {
            items.put(tourId, amount);
        }
        return true;
    }

    public void removeItemFromCart(int tourId) {
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(tourId)) {
            this.items.remove(tourId);
        }

        if (this.travelTour == null) {
            return;
        }
        if (this.travelTour.containsKey(tourId)) {
            this.travelTour.remove(tourId);
        }
    }

    public float getTotalPrice() {
        float totalPrice = 0;
        float total = 0;
        if (travelTour != null && items != null) {
            for (Integer tourId : travelTour.keySet()) {
                if (items != null) {
                    int amount = items.get(tourId);
                    float price = travelTour.get(tourId).getPrice();
                    total += amount * price;   
                }
            }
        }
        float value = 0;
        if(discoutDTO != null){
            value = discoutDTO.getValue();
        }
        
        totalPrice = total - total * (value / 100);
        
        return totalPrice;
    }
    
    public float getDiscountValue(){
        float totalPrice = 0;
        float total = 0;
        if (travelTour != null && items != null) {
            for (Integer tourId : travelTour.keySet()) {
                if (items != null) {
                    int amount = items.get(tourId);
                    float price = travelTour.get(tourId).getPrice();
                    total += amount * price;   
                }
            }
        }
        float value = 0;
        if(discoutDTO != null){
            value = discoutDTO.getValue();
        }
        
        totalPrice = total * (value / 100);
        
        return totalPrice;
    }
    public String getDiscountValueDisplay(){
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(getDiscountValue());
    }
    
    public String getTotalPriceDisplay() {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(getTotalPrice());
    }

    public float getPriceOfItems(int tourId) {
        float priceOfItems = 0;
        if (travelTour != null && items != null) {

            int amount = items.get(tourId);
            float price = travelTour.get(tourId).getPrice();
            priceOfItems = amount * price;

        }
        return priceOfItems;
    }
    public String getPriceOfEachItemDisplay(int tourId) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(getPriceOfItems(tourId));
    }
    
    public String getPriceOfItemsDisplay(int tourId) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(getTotalPrice());
    }


    public String getPriceDisplay(int tourId) {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(travelTour.get(tourId).getPrice());
    }
}
