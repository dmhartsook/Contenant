package edu.ucsb.cs.cs185.contenant.contenant;

import java.io.Serializable;

/**
 * Created by Deanna on 6/2/16.
 */
public class Room implements Serializable {
    private String notes;

    public Room(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
