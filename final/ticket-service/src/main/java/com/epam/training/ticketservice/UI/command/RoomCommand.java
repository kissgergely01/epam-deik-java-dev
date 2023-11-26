package com.epam.training.ticketservice.UI.command;

import com.epam.training.ticketservice.core.room.RoomService;
import com.epam.training.ticketservice.core.room.model.RoomDTO;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class RoomCommand {
    private final RoomService roomService;

    @ShellMethod(key = "create room", value = "Create a new room")
    public String createRoom(String name, Integer numRows, Integer numColumns) {
        try {
            roomService.createRoom(name, numRows, numColumns);
            return "Room creation successful!";
        } catch (Exception e) {
            return "Failed to create room!";
        }
    }

    @ShellMethod(key = "list rooms", value = "List all rooms")
    public String listRooms() {
        List<RoomDTO> rooms = roomService.listRooms();

        if (rooms.isEmpty()) {
            return "There are no rooms at the moment";
        } else {
            StringBuilder result = new StringBuilder();
            for (RoomDTO room : rooms) {
                result.append(String.format("Room %s with %d seats, %d rows, and %d columns\n",
                        room.getName(), room.getNumRows() * room.getNumColumns(), room.getNumRows(), room.getNumColumns()));
            }
            return result.toString();
        }
    }

    @ShellMethod(key = "update room", value = "Update an existing room")
    public String updateRoom(String name, Integer numRows, Integer numColumns) {
        Optional<RoomDTO> updatedRoom = roomService.updateRoom(name, numRows, numColumns);
        return updatedRoom.map(room -> "Room update successful: " + formatRoom(room))
                .orElse("Failed to update room! Room not found.");
    }

    @ShellMethod(key = "delete room", value = "Delete an existing room")
    public String deleteRoom(String name) {
        Optional<RoomDTO> deletedRoom = roomService.deleteRoom(name);
        return deletedRoom.map(room -> "Room deletion successful: " + formatRoom(room))
                .orElse("Failed to delete room! Room not found.");
    }

    private String formatRoom(RoomDTO room) {
        return String.format("%s with %d seats, %d rows, and %d columns",
                room.getName(), room.getNumRows() * room.getNumColumns(), room.getNumRows(), room.getNumColumns());
    }

}
