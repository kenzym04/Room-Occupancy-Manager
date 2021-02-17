package com.smart_host.rooms.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.smart_host.rooms.api.model.Error;
import com.smart_host.rooms.api.model.RoomOccupationOptimizationResponse;
import com.smart_host.rooms.occupancy.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@EnableWebMvc
public class RoomsController {
    private IRoomOccupancyOptimizer optimizer;

    public RoomsController() {
        this.optimizer = new PremiumEconomyPremiumOccupancyOptimizer();
    }

    // /rooms?economy=3&premium=3&anotherRoomType=0
    @RequestMapping(path = "/rooms", method = RequestMethod.GET)
    public ResponseEntity<?> listRoomOccupancyOptimization(@RequestParam Map<String,String> roomTypeAvailabilityMap) throws Exception {
        try {
            List<RoomAvailabilityState> availableRooms = getRoomAvailability(roomTypeAvailabilityMap);
            List<Double> potentialGuests = loadPotentialClients();
            RoomsOccupationState roomsOccupationState = new RoomsOccupationState(availableRooms, potentialGuests);
            roomsOccupationState = optimizer.optimizeRoomOccupation(roomsOccupationState);
            List<RoomOccupationOptimizationResponse> response = RoomOccupationOptimizationResponse.fromRoomOccupationState(roomsOccupationState);

            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.badRequest().body(new Error(exception.getMessage()));
        }
        
    }

    /**
     * Loads potential clients based on a static resource. The URL could be set by configuration or env vars.
     * Left hardcoded in here due to time constrains.
     * @return a list of potential clients containing the amount they are willing to pay for a room.
     */
    private List<Double> loadPotentialClients() {
        RestTemplate potentialGuestsLoader = new RestTemplate();
        String potentialGuestsUrl = "https://gist.githubusercontent.com/fjahr/b164a446db285e393d8e4b36d17f4143/raw/75108c09a72a001a985d27b968a0ac5a867e830b/smarthost_hotel_guests.json";
        ResponseEntity<String> guests = potentialGuestsLoader.getForEntity(potentialGuestsUrl, String.class);
        String list = guests.getBody();
        return potentialClientsFromList(list);
    }

    /**
     * Creates a map of available rooms per room type converting the key into a RoomType and the value into availability
     * @param roomTypeAvailabilityMap
     * @return A list of RoomAvailabilityState
     * @throws Exception When unable to create a RoomType or the availability can't be parsed to int
     */
    private List<RoomAvailabilityState> getRoomAvailability(Map<String, String> roomTypeAvailabilityMap) throws Exception {
        List<RoomAvailabilityState> availability = new ArrayList<>();
        for (Map.Entry<String, String> entry : roomTypeAvailabilityMap.entrySet()) {
            String roomTypeString = entry.getKey().toUpperCase();
            String availabilityString = entry.getValue();
            try {
                RoomAvailabilityState availabilityState = new RoomAvailabilityState(
                        RoomType.valueOf(roomTypeString),
                        Integer.parseInt(availabilityString)
                );
                availability.add(availabilityState);
            } catch (Exception exception) {
                throw new Exception(String.format("Invalid room-type:availability combination %s:%s", roomTypeString, availabilityString));
            }
        }
        return availability;
    }

    /**
     * Finds the double values in the string. The referenced file in the test is a multi-line json
     * @param list an array of numbers
     * @return A list of doubles representing the potential clients for the rooms
     */
    public List<Double> potentialClientsFromList(String list) {
        Pattern numbers = Pattern.compile("\\d+(\\.\\d+)?", Pattern.DOTALL);
        Matcher matcher = numbers.matcher(list);
        List<Double> result = new ArrayList<>();

        while (matcher.find()) {
            try {
                result.add(Double.parseDouble(matcher.group(0)));
            } catch (Exception exception) {
                //Ignore strings that are not numbers
            }
        }

        return result;
    }
}
