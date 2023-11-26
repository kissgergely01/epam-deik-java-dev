package com.epam.training.ticketservice.core.room;


import com.epam.training.ticketservice.core.room.model.RoomDto;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements  RoomService{
    private final RoomRepository roomRepository;

    @Override
    public void createRoom(String name, Integer numRows, Integer numColumns) {
        Room room = new Room(name,numRows,numColumns);
        roomRepository.save(room);
    }

    @Override
    public Optional<RoomDto> updateRoom(String name, Integer numRows, Integer numColumns) {
        return roomRepository.findByName(name)
                .map(room -> {
                    room.setColumnsNumber(numColumns);
                    room.setRowsNumber(numRows);
                    roomRepository.save(room);
                    return new RoomDto(room.getName(), room.getRowsNumber(), room.getColumnsNumber());
                });
    }

    @Override
    public Optional<RoomDto> deleteRoom(String name) {
        return roomRepository.findByName(name)
                .map(room -> {
                    roomRepository.delete(room);
                    return new RoomDto(room.getName(), room.getRowsNumber(), room.getColumnsNumber());
                });
    }

    @Override
    public List<RoomDto> listRooms() {
        return roomRepository.findAll().stream()
                .map(room -> new RoomDto(room.getName(), room.getRowsNumber(), room.getColumnsNumber()))
                .collect(Collectors.toList());
    }
}
