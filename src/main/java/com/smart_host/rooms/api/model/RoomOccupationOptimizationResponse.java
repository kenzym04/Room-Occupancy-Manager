package com.smart_host.rooms.api.model;

import com.smart_host.rooms.occupancy.RoomAvailabilityState;
import com.smart_host.rooms.occupancy.RoomType;
import com.smart_host.rooms.occupancy.RoomsOccupationState;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DAO object that includes the revenue per room. This class is created to have more control over the
 * response sent to the clients. It doesn't have to map one to one with RoomAvailabilityState
 */
public class RoomOccupationOptimizationResponse {
    private RoomType roomType;
    private double revenue;
    private List<Double> guests;

    public RoomOccupationOptimizationResponse(RoomAvailabilityState availabilityState) {
        this.roomType = availabilityState.getRoomType();
        this.revenue = availabilityState.getRevenue();
        this.guests = availabilityState.getOccupancy();
    }

    public static List<RoomOccupationOptimizationResponse> fromRoomOccupationState(RoomsOccupationState roomsOccupationState) {
        return roomsOccupationState.getAvailableRooms()
                .stream()
                .map(RoomOccupationOptimizationResponse::new)
                .collect(Collectors.toList());
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public List<Double> getGuests() {
        return guests;
    }

    public void setGuests(List<Double> guests) {
        this.guests = guests;
    }
}
