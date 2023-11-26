package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.model.RoomDTO;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    void createRoom(String name,Integer RowsNumber,Integer ColumnsNumber);
    Optional<RoomDTO> updateRoom(String name, Integer RowsNumber, Integer ColumnsNumber);

    Optional<RoomDTO> deleteRoom(String name);

    List<RoomDTO> listRooms();

}
