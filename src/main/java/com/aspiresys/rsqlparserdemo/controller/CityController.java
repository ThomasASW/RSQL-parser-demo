package com.aspiresys.rsqlparserdemo.controller;

import com.aspiresys.rsqlparserdemo.dto.CityDTO;
import com.aspiresys.rsqlparserdemo.dto.CreateCityDTO;
import com.aspiresys.rsqlparserdemo.entity.City;
import com.aspiresys.rsqlparserdemo.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public List<CityDTO> getCities(@RequestParam(value = "query", required = false) String query) {
        return cityService.getCities(query);
    }

    @PostMapping
    public City save(@RequestBody CreateCityDTO createCityDTO) {
        return cityService.save(createCityDTO);
    }
}
