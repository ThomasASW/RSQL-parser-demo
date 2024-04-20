package com.aspiresys.rsqlparserdemo.services;

import com.aspiresys.rsqlparserdemo.dto.CreateZipCodeDTO;
import com.aspiresys.rsqlparserdemo.entity.City;
import com.aspiresys.rsqlparserdemo.entity.ZipCode;
import com.aspiresys.rsqlparserdemo.repository.CityRepository;
import com.aspiresys.rsqlparserdemo.repository.ZipCodeRepository;
import com.aspiresys.rsqlparserdemo.utils.CustomRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ZipCodeService {

    @Autowired
    private ZipCodeRepository zipCodeRepository;

    @Autowired
    private CityRepository cityRepository;

    public List<ZipCode> getZipCodes(String query) {
        Specification<ZipCode> specification = null;
        if (Objects.nonNull(query) && !query.isBlank()) {
            Node root = new RSQLParser().parse(query);
            specification = root.accept(new CustomRsqlVisitor<>());
        }
        return zipCodeRepository.findAll(specification);
    }

    public ZipCode save(CreateZipCodeDTO createZipCodeDTO) {
        Optional<City> city = cityRepository.findById(createZipCodeDTO.getCityId());
        if (city.isPresent()) {
            ZipCode zipCode = new ZipCode();
            zipCode.setZipCode(createZipCodeDTO.getZipCode());
            zipCode.setCity(city.get());
            return zipCodeRepository.save(zipCode);
        }
        return null;
    }
}
