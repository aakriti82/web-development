package com.example.cab_booking.Service;

import com.example.cab_booking.Entity.Cars;
import com.example.cab_booking.Entity.User;
import com.example.cab_booking.Pojo.UserPojo;

import java.io.IOException;

public interface UserService {
    UserPojo save(UserPojo userPojo) throws IOException;
    User findByEmail(String email);

    User findById(Integer id);

    UserPojo update(UserPojo userPojo) throws IOException;


}
