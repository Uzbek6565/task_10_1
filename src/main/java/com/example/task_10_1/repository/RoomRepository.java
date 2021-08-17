package com.example.task_10_1.repository;

import com.example.task_10_1.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> getAllByHotelId(Integer hotel_id);
    boolean existsByHotelIdAndFloorAndNumber(Integer hotel_id, Integer floor, Integer number);
}
