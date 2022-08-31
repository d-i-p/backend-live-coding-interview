package com.de.hiking.models;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ApiModel("Booking")
@Entity
@Getter
@Setter
@ToString
@Table(name = "booking", schema = "hiking")
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, name = "booking_id")
    private UUID bookingId;

    @JoinColumn(name = "hiker_id")
    @Column(name = "reserved_by_hiker_id")
    private UUID reservedByHikerId;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @JoinColumn(name = "trail_id")
    private UUID trailId;

    @ManyToMany
    @JoinTable(
            name = "booking_hiker",
            schema = "hiking",
            joinColumns = {@JoinColumn(name = "booking_id")},
            inverseJoinColumns = {@JoinColumn(name = "hiker_id")}
    )
    private Set<Hiker> bookMembers = new HashSet<>();

}
