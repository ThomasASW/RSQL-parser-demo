package com.aspiresys.rsqlparserdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String state_code;

    @JsonManagedReference
    @OneToMany(mappedBy = "state")
    @ToString.Exclude
    private List<City> cities;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Country country;
}
