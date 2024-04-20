package com.aspiresys.rsqlparserdemo.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StateDTO {

    private String name;

    private List<CityDTO> cities;
}
