package edu.ucsb.cs.cs185.contenant.contenant;

import java.util.HashMap;

/**
 * A storage class for houses. Should interact with a database.
 */
public class HouseStorage {
    private static HashMap<Integer, House> houses = new HashMap<>();

    private HouseStorage() {
    }

    /* Stores the passed house by ID. */
    public static void addHouse(House house) {
        houses.put(house.getId(), house);
    }

}
