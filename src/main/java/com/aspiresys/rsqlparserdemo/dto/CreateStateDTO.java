package com.aspiresys.rsqlparserdemo.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateStateDTO {

    private String name;

    private Long countryId;
}
