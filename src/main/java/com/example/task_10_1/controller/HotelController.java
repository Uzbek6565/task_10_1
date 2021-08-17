package com.example.task_10_1.controller;

import com.example.task_10_1.entity.Hotel;
import com.example.task_10_1.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hotel getHotelById(@PathVariable Integer id){
        if (hotelRepository.existsById(id))
            return hotelRepository.getById(id);
        return null;
    }

    @PostMapping
    public String addHotel(@RequestBody Hotel hotel){
        if (hotelRepository.existsByName(hotel.getName()))
            return "Hotel already exists";
        Hotel hotel1 = new Hotel(null, hotel.getName());
        hotelRepository.save(hotel1);
        return "Hotel is added";
    }

    @DeleteMapping("/{id}")
    public String deleteHotel(@PathVariable Integer id){
        if (!hotelRepository.existsById(id))
            return "Hotel not found";
        hotelRepository.deleteById(id);
        return "Hotel is deleted";
    }

    @PutMapping("/{id}")
    public String editHotel(@PathVariable Integer id, @RequestBody Hotel hotel){
        if (!hotelRepository.existsById(id))
            return "Hotel not found";
        if (hotelRepository.existsByName(hotel.getName()))
            return "Hotel already exists";
        Hotel foundHotel = hotelRepository.getById(id);
        foundHotel.setName(hotel.getName());
        return "Hotel data is edited";
    }
}
