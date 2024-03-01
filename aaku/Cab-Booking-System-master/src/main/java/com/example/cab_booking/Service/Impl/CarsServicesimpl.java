package com.example.cab_booking.Service.Impl;

import com.example.cab_booking.Entity.Cars;
import com.example.cab_booking.Pojo.CarPojo;
import com.example.cab_booking.Repo.CarRepo;
import com.example.cab_booking.Service.CarServices;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class CarsServicesimpl implements CarServices {
    private final CarRepo carRepo;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/Cars";

    @Override
    public CarPojo save(CarPojo carpojo) throws IOException {
        Cars cars;
        if (carpojo.getId() != null) {
            cars = carRepo.findById(carpojo.getId()).orElseThrow(() -> new RuntimeException("Not Found"));
        } else {
            cars = new Cars();
        }
        cars.setCarmodel(carpojo.getCarmodel());
        cars.setCar_no(carpojo.getCar_no());
        cars.setPrice(carpojo.getPrice());
        cars.setCarname(carpojo.getCarname());

        if(carpojo.getImage()!=null){
            StringBuilder fileNames = new StringBuilder();
            System.out.println(UPLOAD_DIRECTORY);
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, carpojo.getImage().getOriginalFilename());
            fileNames.append(carpojo.getImage().getOriginalFilename());
            Files.write(fileNameAndPath, carpojo.getImage().getBytes());
            cars.setImage(carpojo.getImage().getOriginalFilename());
        }
        carRepo.save(cars);
        return new CarPojo(cars);
    }

    @Override
    public List<Cars> findAll() {
        return findAllInList(carRepo.findAll());
    }

    @Override
    public Cars findById(Integer id) {
        Cars cars= carRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
        cars=Cars.builder()
                .id(cars.getId())
                .price(cars.getPrice())
                .carmodel(cars.getCarmodel())
                .carname(cars.getCarname())
                .car_no(cars.getCar_no())
                .ImageBase64(getImageBase64(cars.getImage()))
                .build();
        return cars;

    }

    @Override
    public void deleteById(Integer id) {
carRepo.deleteById(id);
    }


    public  List<Cars> findAllInList(List<Cars> hireList){
        Stream<Cars> allJobsWithImage = hireList.stream().map(cars ->
                Cars.builder()
                        .id(cars.getId())
                        .price(cars.getPrice())
                        .carmodel(cars.getCarmodel())
                        .carname(cars.getCarname())
                        .car_no(cars.getCar_no())
                        .ImageBase64(getImageBase64(cars.getImage()))
                        .build()
        );
        hireList = allJobsWithImage.toList();
        return hireList;
    }

    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/Cars/";
        File file = new File(filePath + fileName);
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String base64 = Base64.getEncoder().encodeToString(bytes);
        return base64;
    }

}