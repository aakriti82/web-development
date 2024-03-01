package com.example.cab_booking.Service.Impl;

import com.example.cab_booking.Entity.Cars;
import com.example.cab_booking.Entity.Request;
import com.example.cab_booking.Pojo.RequestPojo;
import com.example.cab_booking.Repo.CarRepo;
import com.example.cab_booking.Repo.RequestRepo;
import com.example.cab_booking.Repo.UserRepo;
import com.example.cab_booking.Service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final UserRepo userRepo;
    private final CarRepo carRepo;
    private final RequestRepo requestRepo;
    @Override
    public RequestPojo save(RequestPojo requestPojo) {
        Request request= new Request();
        if(requestPojo.getId()!=null){
            request.setId(request.getId());
        }
        request.setCars_id(carRepo.findById(requestPojo.getCars_id()).orElseThrow());
        request.setUser_id(userRepo.findById(requestPojo.getUser_id()).orElseThrow());
        request.setPeople(requestPojo.getPeople());
        request.setDateFrom(requestPojo.getDateFrom());
        request.setDateTo(requestPojo.getDateTo());

        requestRepo.save(request);
        return new RequestPojo(request);
    }


    @Override
    public List<Request> findRequestById(Integer id) {
        return findAllInList(requestRepo.findRequestById(id));

}

    @Override
    public List<Request> findAll() {
        return findAllInList(requestRepo.findAll());
    }


    @Override
    public void deleteById(Integer id) {
        requestRepo.deleteById(id);
    }

    @Override
    public Request findById(Integer id) {
        Request request= requestRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
        request=Request.builder()
                .id(request.getId())
                .dateFrom(request.getDateFrom())
                .dateTo(request.getDateTo())
                .user_id(request.getUser_id())
                .cars_id(request.getCars_id())
                .people(request.getPeople())
                .build();
        return request;
    }

    public  List<Request> findAllInList(List<Request> hireList){
        Stream<Request> allJobsWithImage = hireList.stream().map(hire ->
                Request.builder()
                        .id(hire.getId())
                        .dateFrom(hire.getDateFrom())
                        .dateTo(hire.getDateTo())
                        .user_id(hire.getUser_id())
                        .cars_id(hire.getCars_id())
                        .people(hire.getPeople())
                        .build()
        );
        hireList = allJobsWithImage.toList();
        return hireList;
    }
}
