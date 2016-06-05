package edu.ucsb.cs.cs185.contenant.contenant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Deanna on 6/2/16.
 */
public class House implements Serializable {
    private static AtomicInteger nextId = new AtomicInteger();

    private int id;
    private String address;
    private String price;
    private String notes;
    private HashMap<Integer, Room> rooms = new HashMap<>();

    public House() {
        this.id = nextId.incrementAndGet();
    }

    public House(String address, String price, String notes) {
        this.id = nextId.incrementAndGet();
        this.address = address;
        this.price = price;
        this.notes = notes;
    }

    public House(int houseId) {
        this.id = houseId;
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

    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }

    public int getId() {
        return id;
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
