package com.de.hiking.models;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.UUID;

@ApiModel("Trail")
@Entity
@Getter
@Setter
@ToString
@Table(name = "trail", schema = "hiking")
public class Trail {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, name = "trail_id")
    private UUID trailId;

    @Column
    private String name;

    @Column
    private double price;

    @Column(name = "min_age")
    private int minAge;

    @Column(name = "max_age")
    private int maxAge;

    @Column(name = "start_time")
    private LocalTime startTrail;

    @Column(name = "end_time")
    private LocalTime endTrail;

}
