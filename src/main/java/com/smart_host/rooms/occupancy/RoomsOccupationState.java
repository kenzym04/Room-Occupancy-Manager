package com.smart_host.rooms.occupancy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the state of available rooms and potential clients.
 * Defines methods to know the revenue or availability per room type
 */
public class RoomsOccupationState {
    private List<RoomAvailabilityState> availableRooms;
    private List<Double> potentialGuests;

    public RoomsOccupationState(List<RoomAvailabilityState> availableRooms, List<Double> potentialGuests) {
        this.availableRooms = availableRooms;
        this.potentialGuests = potentialGuests;
    }

    public List<RoomAvailabilityState> getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(List<RoomAvailabilityState> availableRooms) {
        this.availableRooms = availableRooms;
    }

    public List<Double> getPotentialGuests() {
        return potentialGuests;
    }

    public void setPotentialGuests(List<Double> potentialGuests) {
        this.potentialGuests = potentialGuests;
    }

    public Map<RoomType, Double> getRoomTypeRevenueMap() {

        Map<RoomType, Double> revenues = new HashMap<>();
        for (RoomAvailabilityState room : availableRooms) {
            revenues.put(room.getRoomType(), room.getRevenue());
        }

        return revenues;
    }

    public double getRevenueByRoomType(RoomType roomType) {
        RoomAvailabilityState roomAvailabilityState = availableRooms.stream()
                .filter(room -> room.getRoomType().equals(roomType))
                .findFirst()
                .orElse(null);

        return roomAvailabilityState == null ? 0 : roomAvailabilityState.getRevenue();
    }

    public int getAvailableRoomsByType(RoomType roomType) {
        return availableRooms.stream()
                .filter(room -> room.getRoomType().equals(roomType))
                .map(RoomAvailabilityState::getAvailability)
                .findAny()
                .orElse(0);
    }

    public RoomAvailabilityState getRoomAvailabilityStateByType(RoomType roomType) {
        return availableRooms.stream()
                .filter(room -> room.getRoomType().equals(roomType))
                .findAny()
                .orElse(null);
    }
}
