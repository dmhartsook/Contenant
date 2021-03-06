package edu.ucsb.cs.cs185.contenant.contenant;

import android.util.Log;

import java.util.Collection;
import java.util.HashMap;

/**
 * A storage class for houses. Should interact with a database.
 */
public class HouseStorage {
    private static HashMap<Long, House> housesHashMap = new HashMap<>();

    private HouseStorage() {
    }

    /* Stores the passed house by ID. */
    public static void addHouse(House house) {
        housesHashMap.put(house.getId(), house);
    }

    public static House getHouse(long houseId) {
        return housesHashMap.get(houseId);
    }

    public static void addRoomToHouse(Room room, long myHouseId) {
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
