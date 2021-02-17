package com.smart_host.rooms.occupancy;

import java.util.ArrayList;
import java.util.List;

/**
 * Assigns room occupation with the following rules
 * 1. Premium guests will be assigned to premium rooms using highest price sorting
 * 2. Economy guests will be assigned to economy rooms using highest price sorting
 * 3. Economy guests will be assigned to premium rooms only if all economy rooms have been assigned
 * and there are premium rooms available. Only the highest paying economy guests are eligible for upgrade.
 */
public class PremiumEconomyPremiumOccupancyOptimizer implements IRoomOccupancyOptimizer {
    List<IRoomOccupancyOptimizer> optimizers = new ArrayList<>();

    public PremiumEconomyPremiumOccupancyOptimizer() {
        optimizers.add(new RoomOccupancyHighestPriceFirstOptimizer(Double.MAX_VALUE, 99, RoomType.PREMIUM));
        optimizers.add(new RoomOccupancyHighestPriceFirstOptimizer(100, 0, RoomType.ECONOMY));
        optimizers.add(new RoomOccupancyHighestPriceFirstOptimizer(100, 0, RoomType.PREMIUM));
    }

    @Override
    public RoomsOccupationState optimizeRoomOccupation(RoomsOccupationState roomsOccupationState) {
        // Occupy all premium rooms first
        roomsOccupationState = optimizers.get(0).optimizeRoomOccupation(roomsOccupationState);
        int numberOfUpgrades = getNumberOfPremiumUpgrades(roomsOccupationState);
        if (numberOfUpgrades > 0) {
            // Upgrade economy to premium
            roomsOccupationState = optimizers.get(2).optimizeRoomOccupation(roomsOccupationState);
        }
        // assign economy
        roomsOccupationState = optimizers.get(1).optimizeRoomOccupation(roomsOccupationState);
        return roomsOccupationState;
    }

    private int getNumberOfPremiumUpgrades(RoomsOccupationState roomsOccupationState) {
        int availableEconomy = roomsOccupationState.getAvailableRoomsByType(RoomType.ECONOMY);
        int potentialGuests = roomsOccupationState.getPotentialGuests().size();
        return potentialGuests - availableEconomy;
    }
}
