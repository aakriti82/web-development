package com.example.cab_booking.Repo;

import com.example.cab_booking.Entity.Cars;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepo extends JpaRepository<Cars, Integer> {
}
