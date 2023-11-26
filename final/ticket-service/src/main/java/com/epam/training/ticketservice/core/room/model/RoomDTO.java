package com.epam.training.ticketservice.core.room.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Data
@Getter
@Setter
@Value
public class RoomDTO {
    private final String name;
    private final int numRows;
    private final int numColumns;
}
