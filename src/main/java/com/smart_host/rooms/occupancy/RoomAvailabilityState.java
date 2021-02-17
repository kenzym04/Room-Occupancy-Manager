package com.smart_host.rooms.occupancy;

import java.util.ArrayList;
import java.util.List;

/**
 * It represents the current status of a hotel room. It has the room type, the number of available beds, and the price
 * paid in for the occupied beds.
 */
public class RoomAvailabilityState {
    private RoomType roomType;

    private int availability;

    private List<Double> occupancy;

    public RoomAvailabilityState(RoomType roomType, int availability, List<Double> occupancy) {
        this.roomType = roomType;
        this.availability = availability;
        this.occupancy = occupancy;
    }

    public RoomAvailabilityState(RoomType roomType, int availability) {
        this(roomType, availability, new ArrayList<>());
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public List<Double> getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(List<Double> occupancy) {
        this.occupancy = occupancy;
    }

    public double getRevenue() {
        return occupancy.stream().reduce((x, y) -> x + y).orElse((double) 0);
    }

    // Assigns guests to rooms based on availability in order
    public int assignGuests(List<Double> guests) {
        int i = 0;
        int available = availability;
        for (; i < available && i < guests.size(); i++) {
            occupancy.add(guests.get(i));
            setAvailability(availability - 1);
        }
        return i;
    }
}
