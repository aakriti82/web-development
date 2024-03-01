package com.example.cab_booking.Service;

import com.example.cab_booking.Entity.Request;
import com.example.cab_booking.Pojo.RequestPojo;

import java.util.List;

public interface RequestService {
    RequestPojo save(RequestPojo requestPojo);
    List<Request> findRequestById(Integer id);
    List<Request>  findAll();
    void deleteById(Integer id);
    Request findById(Integer id);


}
