package com.example.cab_booking.Service;

import com.example.cab_booking.Entity.Cars;
import com.example.cab_booking.Pojo.CarPojo;

import java.io.IOException;
import java.util.List;

public interface CarServices {
    CarPojo save(CarPojo tripPojo) throws IOException;
    List<Cars> findAll();
    Cars findById(Integer id);
    void deleteById(Integer id);
}
