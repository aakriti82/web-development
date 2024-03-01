package com.example.cab_booking.Entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="request")
public class Request {
    @Id
    @SequenceGenerator(name = "request_seq_gen", sequenceName = "request_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "request_seq_gen", strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(name = "dateFrom")
    private String dateFrom;


    @Column(name = "dateTo")
    private String dateTo;

    @Column(name = "people")
    private String people;

    @ManyToOne
    @JoinColumn(name = "cars_FK", referencedColumnName = "id")
    private Cars cars_id;
    @ManyToOne
    @JoinColumn(name = "user_FK", referencedColumnName = "id")
    private User user_id;

}
