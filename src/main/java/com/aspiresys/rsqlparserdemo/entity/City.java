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
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private State state;

    @JsonManagedReference
    @OneToMany(mappedBy = "city")
    @ToString.Exclude
    private List<ZipCode> zipCodes;
}
