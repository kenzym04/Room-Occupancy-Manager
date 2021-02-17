package com.smart_host.rooms.occupancy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomOccupancyHighestPriceFirstOptimizerTest {

    @Test
    void optimizeRoomOccupation() {
        RoomAvailabilityState economyRooms = new RoomAvailabilityState(RoomType.ECONOMY, 2);
        List<RoomAvailabilityState> availableRooms = Arrays.asList(economyRooms);

        List<Double> potentialGuests = Arrays.asList(40.0, 120.0, 60.0);
        RoomsOccupationState roomsOccupationState = new RoomsOccupationState(availableRooms, potentialGuests);

        RoomOccupancyHighestPriceFirstOptimizer optimizer = new RoomOccupancyHighestPriceFirstOptimizer(100, 0, RoomType.ECONOMY);

        RoomsOccupationState actual = optimizer.optimizeRoomOccupation(roomsOccupationState);
        assertNotNull(actual);
        assertEquals(1, actual.getRoomTypeRevenueMap().size());
        Double economyRoomRevenue = actual.getRoomTypeRevenueMap().get(RoomType.ECONOMY);
        assertEquals(100.0, economyRoomRevenue.doubleValue());
    }
}