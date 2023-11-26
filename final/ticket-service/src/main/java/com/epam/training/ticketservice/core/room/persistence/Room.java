package com.epam.training.ticketservice.core.room.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "Rooms")
@Data
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique = true)
    private String name;
    private Integer RowsNumber;
    private Integer ColumnsNumber;
    public Room(String name, Integer RowsNumber, Integer ColumnsNumber) {
        this.name = name;
        this.RowsNumber = RowsNumber;
        this.ColumnsNumber = ColumnsNumber;
    }
}
