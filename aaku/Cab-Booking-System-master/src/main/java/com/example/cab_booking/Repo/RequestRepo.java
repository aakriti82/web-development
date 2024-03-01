package com.example.cab_booking.Repo;

import com.example.cab_booking.Entity.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RequestRepo extends JpaRepository<Request,Integer> {
    @Query(value = "SELECT * FROM request where user_FK=?1", nativeQuery = true)
    List<Request> findRequestById(Integer id);
}
