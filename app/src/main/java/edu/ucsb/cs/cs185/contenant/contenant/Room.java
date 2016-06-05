package edu.ucsb.cs.cs185.contenant.contenant;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Deanna on 6/2/16.
 */
public class Room implements Serializable {
    private static AtomicInteger nextId = new AtomicInteger();

    private final int id;
    private final int houseId;
    private String notes;
    private int typeIndex; // The index in the room_array

    public Room(int houseId, String notes) {
        this.id = nextId.incrementAndGet();
        this.houseId = houseId;
        this.notes = notes;
    }

    public Room(int houseId) {
        this.id = nextId.incrementAndGet();
        this.houseId = houseId;
    }

    public String getNotes() {
        return notes;
    }

    public int getId() {
        return id;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getHouseId() {
        return houseId;
    }

    public int getTypeIndex() {
        return typeIndex;
    }

    public void setTypeIndex(int typeIndex) {
        this.typeIndex = typeIndex;
    }
}
