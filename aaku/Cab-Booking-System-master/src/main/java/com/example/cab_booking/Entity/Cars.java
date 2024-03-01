package com.example.cab_booking.Entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cars")
public class Cars {
    @Id
    @SequenceGenerator(name = "cars_seq_gen", sequenceName = "cars_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "cars_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "carname")
    private String carname;

    @Column(name = "carmodel")
    private String carmodel;

    @Column(name = "car_no")
    private String car_no;

    @Column(name = "price")
    private String price;

    @Column(name="image")
    private String image;

    @Transient
    private String ImageBase64;



}
