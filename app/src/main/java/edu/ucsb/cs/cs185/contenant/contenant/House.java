package edu.ucsb.cs.cs185.contenant.contenant;

import java.io.File;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Deanna on 6/2/16.
 */
public class House implements Serializable {
    private static AtomicLong nextId = new AtomicLong();

    private long id;
    private String name;
    private String address;
    private String price;
    private String notes;
    private HashMap<Integer, Room> rooms = new HashMap<>();
    private String image;

    public House() {
        this.id = nextId.incrementAndGet();
    }

    public House(String name, String address, String price, String notes, String image) {
        this.id = nextId.incrementAndGet();
        this.name = name;
        this.address = address;
        this.price = price;
        this.notes = notes;
        this.image = image;
    }

    public House(int houseId) {
        this.id = houseId;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setImage(String image) {
        this.image = image;
    }

    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getImage() {
        return image;
    }

    public Collection<Room> getRooms() {
        return rooms.values();
    }

    public Room getRoom(int roomId) {
        return rooms.get(roomId);
    }

}
