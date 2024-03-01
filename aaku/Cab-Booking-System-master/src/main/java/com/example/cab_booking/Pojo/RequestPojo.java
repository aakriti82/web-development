package com.example.cab_booking.Pojo;

import com.example.cab_booking.Entity.Request;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestPojo{
    private Integer id;
    private  int user_id;
    private  String dateFrom;
    private  String dateTo;
    private  int cars_id;
    private  String people;

    public RequestPojo(Request request) {
        this.id=request.getId();
        this.user_id=request.getUser_id().getId();
        this.cars_id=request.getCars_id().getId();
        this.dateFrom=request.getDateFrom();
        this.dateTo=request.getDateTo();
    }
}
