package com.aspiresys.rsqlparserdemo.entity;

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
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String iso3;

    private String iso2;

    @JsonManagedReference
    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    private List<State> states;
}
