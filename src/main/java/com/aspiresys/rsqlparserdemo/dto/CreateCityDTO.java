package com.aspiresys.rsqlparserdemo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateCityDTO {

    private String name;

    private Long stateId;
}
