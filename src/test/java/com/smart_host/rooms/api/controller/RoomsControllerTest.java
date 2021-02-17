package com.smart_host.rooms.api.controller;

import com.smart_host.rooms.api.model.RoomOccupationOptimizationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RoomsControllerTest {
    private RoomsController target = new RoomsController();
    @Test
    void listRoomOccupancyOptimization() throws Exception {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("economy", "3");
        queryParams.put("premium", "3");
        ResponseEntity<List<RoomOccupationOptimizationResponse>> responseEntity = (ResponseEntity<List<RoomOccupationOptimizationResponse>>) target.listRoomOccupancyOptimization(queryParams);
        List<RoomOccupationOptimizationResponse> response = responseEntity.getBody();
        assertEquals(2, response.size());
    }

    @Test
    void listOfDoublesFromString() {
        String list = String.format("[\n23,\n45,\n155,\n374,\n22,\n99,\n100,\n101,\n115,\n209\n]");
        List<Double> actual = target.potentialClientsFromList(list);
        double[] expected = new double[]{23.0, 45.0, 155.0, 374.0, 22.0, 99.0, 100.0, 101.0, 115.0, 209.0};
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], actual.get(i).doubleValue());
        }
    }
}