package com.epam.training.ticketservice;

import com.epam.training.ticketservice.core.room.RoomServiceImpl;
import com.epam.training.ticketservice.core.room.model.RoomDTO;
import com.epam.training.ticketservice.core.room.persistence.Room;
import com.epam.training.ticketservice.core.room.persistence.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class RoomTest {
    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateRoom() {
        // Arrange
        String roomLabel = "ClassroomA";
        int seatRows = 6;
        int seatColumns = 10;

        // Act
        roomService.createRoom(roomLabel, seatRows, seatColumns);

        // Assert
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testModifyRoom() {
        // Arrange
        String roomLabel = "ClassroomA";
        int seatRows = 6;
        int seatColumns = 10;
        Room currentRoom = new Room(roomLabel, seatRows, seatColumns);

        when(roomRepository.findByName(roomLabel)).thenReturn(Optional.of(currentRoom));

        // Act
        Optional<RoomDTO> modifiedRoomDto = roomService.updateRoom(roomLabel, seatRows + 1, seatColumns + 1);

        // Assert
        assertTrue(modifiedRoomDto.isPresent());
        assertEquals(seatRows + 1, modifiedRoomDto.get().getNumRows());
        assertEquals(seatColumns + 1, modifiedRoomDto.get().getNumColumns());
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testModifyNonexistentRoom() {
        // Arrange
        String roomLabel = "NonExistentClassroom";
        int seatRows = 6;
        int seatColumns = 10;

        when(roomRepository.findByName(roomLabel)).thenReturn(Optional.empty());

        // Act
        Optional<RoomDTO> modifiedRoomDto = roomService.updateRoom(roomLabel, seatRows, seatColumns);

        // Assert
        assertTrue(modifiedRoomDto.isEmpty());
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void testEraseRoom() {
        // Arrange
        String roomLabel = "RoomToDelete";
        int seatRows = 6;
        int seatColumns = 10;
        Room existingRoom = new Room(roomLabel, seatRows, seatColumns);

        when(roomRepository.findByName(roomLabel)).thenReturn(Optional.of(existingRoom));

        // Act
        Optional<RoomDTO> erasedRoomDto = roomService.deleteRoom(roomLabel);

        // Assert
        assertTrue(erasedRoomDto.isPresent());
        assertEquals(roomLabel, erasedRoomDto.get().getName());
        verify(roomRepository, times(1)).delete(any(Room.class));
    }

    @Test
    void testEraseNonexistentRoom() {
        // Arrange
        String roomLabel = "NonExistentRoom";

        when(roomRepository.findByName(roomLabel)).thenReturn(Optional.empty());

        // Act
        Optional<RoomDTO> erasedRoomDto = roomService.deleteRoom(roomLabel);

        // Assert
        assertTrue(erasedRoomDto.isEmpty());
        verify(roomRepository, never()).delete(any(Room.class));
    }

    @Test
    void testInspectRooms() {
        // Arrange
        when(roomRepository.findAll()).thenReturn(Collections.singletonList(new Room("ClassroomA", 6, 10)));

        // Act
        List<RoomDTO> roomDtoList = roomService.listRooms();

        // Assert
        assertNotNull(roomDtoList);
        assertFalse(roomDtoList.isEmpty());
        verify(roomRepository, times(1)).findAll();
    }
}
