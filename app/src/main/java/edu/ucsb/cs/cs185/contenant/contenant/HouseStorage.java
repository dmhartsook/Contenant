package edu.ucsb.cs.cs185.contenant.contenant;

import android.util.Log;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * A storage class for houses. Should interact with a database.
 */
public class HouseStorage {
    private static HashMap<Integer, House> housesHashMap = new HashMap<>();

    private HouseStorage() {
    }

    /* Stores the passed house by ID. */
    public static void addHouse(House house) {
        housesHashMap.put(house.getId(), house);
    }

    public static House getHouse(int houseId) {
        return housesHashMap.get(houseId);
    }

    public static void addRoomToHouse(Room room, int myHouseId) {
        House house = housesHashMap.get(myHouseId);
        if (house != null) {
            house.addRoom(room);
            addHouse(house);
            housesHashMap.remove(house);
        } else {
            Log.e("House Storage", "House should not be null. Every room needs a house.");
        }
    }

    public static int getNumberOfHouses() {
        return housesHashMap.size();
    }

    public static Collection getHousesCollection() {
        return housesHashMap.values();
    }
}
