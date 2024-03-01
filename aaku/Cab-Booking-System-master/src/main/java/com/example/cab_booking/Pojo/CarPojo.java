package com.example.cab_booking.Pojo;

import com.example.cab_booking.Entity.Cars;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CarPojo {
    private Integer id;
    private String carname;
    private String carmodel;
    private  String car_no;
    private  String price;
    private MultipartFile image;

    public CarPojo(Cars trip){
        this.id=trip.getId();
        this.carname=trip.getCarname();
        this.car_no=trip.getCar_no();
        this.carmodel=trip.getCarmodel();
        this.price=trip.getPrice();
    }

}
