package edu.ucsb.cs.cs185.contenant.contenant;

import java.io.Serializable;

/**
 * Created by Deanna on 6/2/16.
 */
public class House implements Serializable {
    private String address;
    private String price;
    private String notes;

    public House(String address, String price, String notes) {
        this.address = address;
        this.price = price;
        this.notes = notes;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getAddress() {
        return address;
    }

    public String getPrice() {
        return price;
    }

    public String getNotes() {
        return notes;
    }
}