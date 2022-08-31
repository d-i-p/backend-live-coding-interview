package com.de.hiking.models;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@ApiModel("Hiker")
@Entity
@Getter
@Setter
@ToString
@Table(name = "hiker", schema = "hiking")
public class Hiker {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, name = "hiker_id")
    private UUID hikerId;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    private String mail;

    @Column
    private int age;

}
