package com.aspiresys.rsqlparserdemo.controller;

import com.aspiresys.rsqlparserdemo.dto.CountryDTO;
import com.aspiresys.rsqlparserdemo.dto.CreateCountryDTO;
import com.aspiresys.rsqlparserdemo.entity.Country;
import com.aspiresys.rsqlparserdemo.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/country")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @GetMapping
    public List<CountryDTO> getCountries(@RequestParam(value = "query", required = false) String query) {
        return countryService.getCountries(query);
    }

    @PostMapping
    public Country save(@RequestBody CreateCountryDTO createCountryDTO) {
        return countryService.save(createCountryDTO.getName());
    }
}
