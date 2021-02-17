package com.smart_host.rooms.occupancy;

/**
 * Represents a strategy to optimize room occupancy
 */
public interface IRoomOccupancyOptimizer {
    RoomsOccupationState optimizeRoomOccupation(RoomsOccupationState roomsOccupationState);
}
