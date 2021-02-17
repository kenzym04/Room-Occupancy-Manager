package com.smart_host.rooms.occupancy;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PremiumEconomyPremiumOccupancyOptimizerTest {
    List<Double> potentialGuests = Arrays.asList(23.0, 45.0, 155.0, 374.0, 22.0, 99.0, 100.0, 101.0, 115.0, 209.0);

    PremiumEconomyPremiumOccupancyOptimizer target = new PremiumEconomyPremiumOccupancyOptimizer();

    void runTest(int premiumRoomsAvailability, int economyRoomsAvailability, double expectedPremiumRevenue, double expectedEconomyRevenue) {
        RoomAvailabilityState premiumRooms = new RoomAvailabilityState(RoomType.PREMIUM, premiumRoomsAvailability);
        RoomAvailabilityState economyRooms = new RoomAvailabilityState(RoomType.ECONOMY, economyRoomsAvailability);
        List<RoomAvailabilityState> availableRooms = Arrays.asList(premiumRooms, economyRooms);

        RoomsOccupationState roomsOccupationState = new RoomsOccupationState(availableRooms, potentialGuests);

        RoomsOccupationState actual = target.optimizeRoomOccupation(roomsOccupationState);

        double actualEconomyRoomRevenue = actual.getRevenueByRoomType(RoomType.ECONOMY);
        assertEquals(expectedEconomyRevenue, actualEconomyRoomRevenue);
        double actualPremiumRoomRevenue = actual.getRevenueByRoomType(RoomType.PREMIUM);
        assertEquals(expectedPremiumRevenue, actualPremiumRoomRevenue);
    }

    /**
     * Test 1
     * Free Premium rooms: 3 Free Economy rooms: 3
     * Usage Premium: 3 (EUR 738) Usage Economy: 3 (EUR 167)
      */
    @Test
    void testOne() {
        runTest(3, 3, 738, 167);
    }

    /**
     * Test 2
     * Free Premium rooms: 7 Free Economy rooms: 5
     * Usage Premium: 6 (EUR 1054) Usage Economy: 4 (EUR 189)
     */
    @Test
    void testTwo() {
        runTest(7, 5, 1054, 189);
    }

    /**
     * Test 3
     * Free Premium rooms: 2 Free Economy rooms: 7
     * Usage Premium: 2 (EUR 583) Usage Economy: 4 (EUR 189)
     */
    @Test
    void testThree() {
        runTest(2, 7, 583, 189);
    }

    /**
     * Test 4
     * Free Premium rooms: 7 Free Economy rooms: 1
     * Usage Premium: 7 (EUR 1153) Usage Economy: 1 (EUR 45)
     */
    @Test
    void testFour() {
        runTest(7, 1, 1153, 45);
    }
}