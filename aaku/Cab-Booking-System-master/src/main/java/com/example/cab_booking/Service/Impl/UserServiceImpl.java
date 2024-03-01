package com.example.cab_booking.Service.Impl;

import com.example.cab_booking.Config.PasswordEncoderUtil;
import com.example.cab_booking.Entity.User;
import com.example.cab_booking.Exception.AppException;
import com.example.cab_booking.Pojo.UserPojo;
import com.example.cab_booking.Repo.UserRepo;
import com.example.cab_booking.Service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
public class UserServiceImpl implements UserService {
    private  final UserRepo userRepo;

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/AllUsers";


    @Override
    public UserPojo save(UserPojo userPojo) throws IOException {
        User user;
        if (userPojo.getId() != null) {
            user = userRepo.findById(userPojo.getId()).orElseThrow(() -> new RuntimeException("Not Found"));
        } else {
            user = new User();
        }
        if(userPojo.getId()!=null){
            user.setId(user.getId());
        }
        user.setEmail(userPojo.getEmail());
        user.setFullname(userPojo.getFullname());
        user.setUsername(userPojo.getUsername());
        user.setPhone(userPojo.getPhone());
        user.setPassword(PasswordEncoderUtil.getInstance().encode(userPojo.getPassword()));

        if(userPojo.getImage()!=null){
            StringBuilder fileNames = new StringBuilder();
            System.out.println(UPLOAD_DIRECTORY);
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, userPojo.getImage().getOriginalFilename());
            fileNames.append(userPojo.getImage().getOriginalFilename());
            Files.write(fileNameAndPath, userPojo.getImage().getBytes());
            user.setImage(userPojo.getImage().getOriginalFilename());
        }

        userRepo.save(user);
        return new UserPojo(user);
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new AppException("Invalid User email", HttpStatus.BAD_REQUEST));
        return user;
    }

    @Override
    public User findById(Integer id) {
        User user= userRepo.findById(id).orElseThrow(()->new RuntimeException("not found"));
        user=User.builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .email(user.getEmail())
                .username(user.getUsername())
                .phone(user.getPhone())
                .imageBase64(getImageBase64(user.getImage()))
                .build();
        return user;
    }

    @Override
    public UserPojo update(UserPojo userPojo) throws IOException {
        User user;
        if (userPojo.getId() != null) {
            user = userRepo.findById(userPojo.getId()).orElseThrow(() -> new RuntimeException("Not Found"));
        } else {
            user = new User();
        }
        if(userPojo.getId()!=null){
            user.setId(user.getId());
        }
        user.setEmail(userPojo.getEmail());
        user.setFullname(userPojo.getFullname());
        user.setUsername(userPojo.getUsername());
        user.setPhone(userPojo.getPhone());
        user.setPassword(PasswordEncoderUtil.getInstance().encode(userPojo.getPassword()));

        if(userPojo.getImage()!=null){
            StringBuilder fileNames = new StringBuilder();
            System.out.println(UPLOAD_DIRECTORY);
            Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, userPojo.getImage().getOriginalFilename());
            fileNames.append(userPojo.getImage().getOriginalFilename());
            Files.write(fileNameAndPath, userPojo.getImage().getBytes());
            user.setImage(userPojo.getImage().getOriginalFilename());
        }


        userRepo.save(user);
        return new UserPojo(user);
    }

    public List<User> findAllInList(List<User> list) {
        Stream<User> allWithImage = list.stream().map(user ->
                User.builder()
                        .id(user.getId())
                        .fullname(user.getFullname())
                        .email(user.getEmail())
                        .username(user.getUsername())
                        .phone(user.getPhone())
                        .imageBase64(getImageBase64(user.getImage()))
                        .build()
        );
        list = allWithImage.toList();
        return list;
    }
    public String getImageBase64(String fileName) {
        String filePath = System.getProperty("user.dir") + "/AllUsers/";
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

