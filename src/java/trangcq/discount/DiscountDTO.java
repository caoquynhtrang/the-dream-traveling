/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trangcq.discount;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author USER
 */
public class DiscountDTO implements Serializable {

    private int id;
    private String code;
    private Date expiredDate;
    private float value;

    public DiscountDTO() {
    }

    public DiscountDTO(int id, String code) {
        this.id = id;
        this.code = code;
    }

    public DiscountDTO(int id, String code, Date expiredDate, float value) {
        this.id = id;
        this.code = code;
        this.expiredDate = expiredDate;
        this.value = value;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

}
