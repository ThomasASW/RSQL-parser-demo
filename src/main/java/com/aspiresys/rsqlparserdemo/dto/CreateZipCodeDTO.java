package com.aspiresys.rsqlparserdemo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateZipCodeDTO {

    private String zipCode;

    private Long cityId;
}
