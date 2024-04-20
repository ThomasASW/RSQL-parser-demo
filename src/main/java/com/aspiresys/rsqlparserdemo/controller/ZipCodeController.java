package com.aspiresys.rsqlparserdemo.controller;

import com.aspiresys.rsqlparserdemo.dto.CreateZipCodeDTO;
import com.aspiresys.rsqlparserdemo.entity.ZipCode;
import com.aspiresys.rsqlparserdemo.services.ZipCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zipCode")
public class ZipCodeController {

    @Autowired
    private ZipCodeService zipCodeService;

    @GetMapping
    public List<ZipCode> getZipCodes(@RequestParam(value = "query", required = false) String query) {
        return zipCodeService.getZipCodes(query);
    }

    @PostMapping
    public ZipCode save(@RequestBody CreateZipCodeDTO createZipCodeDTO) {
        return zipCodeService.save(createZipCodeDTO);
    }
}
