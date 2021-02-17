package com.smart_host.rooms.occupancy;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Optimizes room occupation by sorting in descending order the potential customers and assigns them to the rooms.
 * It includes a price range so that only paying customers in the range are eligible for assignation
 */
public class RoomOccupancyHighestPriceFirstOptimizer implements IRoomOccupancyOptimizer {
    private double upperLimit;
    private double lowerLimit;
    private RoomType roomType;

    public RoomOccupancyHighestPriceFirstOptimizer(double upperLimit, double lowerLimit, RoomType roomType) {
        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.roomType = roomType;
    }

    public double getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(double upperLimit) {
        this.upperLimit = upperLimit;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(double lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public RoomsOccupationState optimizeRoomOccupation(RoomsOccupationState roomsOccupationState) {
        List<Double> potentialGuests = roomsOccupationState.getPotentialGuests();
        if (potentialGuests.size() < 1) {
            return roomsOccupationState;
        }
        // sort by descent order
        potentialGuests.sort(Collections.reverseOrder());

        RoomAvailabilityState roomAvailability = roomsOccupationState.getRoomAvailabilityStateByType(roomType);
        int availability = roomAvailability.getAvailability();

        // Keep the guests that won't get a room assigned to merge them later
        List<Double> deniedGuests = potentialGuests.stream()
                .filter(price -> upperLimit <= price || price <= lowerLimit)
                .collect(Collectors.toList());

        // Find the eligible guests
        List<Double> availableGuests = potentialGuests.stream()
                .filter(price -> upperLimit > price && price > lowerLimit)
                .collect(Collectors.toList());

        int assignedGuests = roomAvailability.assignGuests(availableGuests);

        // Keep the eligible guests that were not assigned
        List<Double> unassignedGuests = availableGuests.stream()
                .skip(assignedGuests)
                .collect(Collectors.toList());

        // Merge guests without room
        List<Double> newPotentialGuests = Stream.concat(deniedGuests.stream(), unassignedGuests.stream())
                .collect(Collectors.toList());

        roomsOccupationState.setPotentialGuests(newPotentialGuests);
        return roomsOccupationState;
    }
}
