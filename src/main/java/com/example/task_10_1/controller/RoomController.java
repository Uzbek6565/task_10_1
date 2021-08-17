package com.example.task_10_1.controller;

import com.example.task_10_1.entity.Room;
import com.example.task_10_1.payload.RoomDto;
import com.example.task_10_1.repository.HotelRepository;
import com.example.task_10_1.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomRepository roomRepository;

    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/{hotel_id}")
    public Page<Room> getAllRoomsByHotelId(@PathVariable Integer hotel_id, @RequestParam int page) {
        if (hotelRepository.existsById(hotel_id)) {
            Pageable pageable = PageRequest.of(page, 10);
            Page<Room> all = roomRepository.findAll(pageable);
            return all;
            //return roomRepository.getAllByHotelId(hotel_id);
        }
        return null;
    }

    @PostMapping
    public String addRoom(@RequestBody RoomDto roomDto) {
        if (roomRepository.existsByHotelIdAndFloorAndNumber(roomDto.getHotel_id(), roomDto.getFloor(), roomDto.getNumber()))
            return "This room in this hotel already exists";
        if (!hotelRepository.existsById(roomDto.getHotel_id()))
            return "Hotel not found";
        Room room = new Room(null, roomDto.getNumber(), roomDto.getFloor(), roomDto.getSize(), hotelRepository.getById(roomDto.getHotel_id()));
        roomRepository.save(room);
        return "Room is saved";
    }

    @DeleteMapping("/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        if (!roomRepository.existsById(id))
            return "Room not found";
        roomRepository.deleteById(id);
        return "Room is deleted";
    }

    @PutMapping("/{id}")
    public String editRoom(@PathVariable Integer id, @RequestBody RoomDto roomDto) {
        if (!roomRepository.existsById(id))
            return "Room not found";
        if (roomRepository.existsByHotelIdAndFloorAndNumber(roomDto.getHotel_id(), roomDto.getFloor(), roomDto.getNumber()))
            return "This room in this hotel already exists";
        if (!hotelRepository.existsById(roomDto.getHotel_id()))
            return "Hotel not found";
        Room room = roomRepository.getById(id);
        room.setNumber(roomDto.getNumber());
        room.setFloor(roomDto.getFloor());
        room.setSize(roomDto.getSize());
        room.setHotel(hotelRepository.getById(roomDto.getHotel_id()));
        roomRepository.save(room);
        return "Room data is editted";
    }
}
