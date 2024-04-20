package com.aspiresys.rsqlparserdemo.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CountryDTO {

    private String name;

    private List<StateDTO> states;
}
