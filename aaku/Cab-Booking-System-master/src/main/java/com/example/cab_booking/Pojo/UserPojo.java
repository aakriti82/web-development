package com.example.cab_booking.Pojo;

import com.example.cab_booking.Entity.User;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPojo {
    private Integer id;

    private String email;
    private  String username;
    private  String fullname;

    private  String phone;

    private  String password;
    private  MultipartFile image;


    public UserPojo(User user) {
        this.id=user.getId();
        this.email=user.getEmail();
        this.fullname=user.getFullname();
        this.password=user.getPassword();
        this.username=user.getUsername();
        this.phone=user.getPhone();
    }
}