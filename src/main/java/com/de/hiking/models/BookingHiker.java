package com.de.hiking.models;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@ApiModel("BookingHiker")
@Entity
@Getter
@Setter
@Table(name = "booking_hiker", schema = "hiking")
public class BookingHiker implements Serializable {

    @Id
    @JoinColumn(name = "hiker_id")
    @Column(name = "hiker_id")
    private UUID hikerId;

    @Id
    @JoinColumn(name = "booking_id")
    @Column(name = "booking_id")
    private UUID bookingId;

}
