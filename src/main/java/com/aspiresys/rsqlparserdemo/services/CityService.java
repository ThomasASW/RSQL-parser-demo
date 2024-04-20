package com.aspiresys.rsqlparserdemo.services;

import com.aspiresys.rsqlparserdemo.dto.CityDTO;
import com.aspiresys.rsqlparserdemo.dto.CreateCityDTO;
import com.aspiresys.rsqlparserdemo.entity.City;
import com.aspiresys.rsqlparserdemo.entity.State;
import com.aspiresys.rsqlparserdemo.entity.ZipCode;
import com.aspiresys.rsqlparserdemo.repository.CityRepository;
import com.aspiresys.rsqlparserdemo.repository.StateRepository;
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
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private StateRepository stateRepository;

    public List<CityDTO> getCities(String query) {
        Specification<City> specification = null;
        if (Objects.nonNull(query) && !query.isBlank()) {
            Node root = new RSQLParser().parse(query);
            specification = root.accept(new CustomRsqlVisitor<>());
        }
        List<City> cities = cityRepository.findAll(specification);
        return cities.stream().map(city -> {
            CityDTO cityDTO = new CityDTO();
            cityDTO.setName(city.getName());
            cityDTO.setZipCodes(
                    city.getZipCodes().stream().map(ZipCode::getZipCode).toList()
            );
            return cityDTO;
        }).toList();
    }

    public City save(CreateCityDTO createCityDTO) {
        Optional<State> state = stateRepository.findById(createCityDTO.getStateId());
        if (state.isPresent()) {
            City city = new City();
            city.setName(createCityDTO.getName());
            city.setState(state.get());
            return cityRepository.save(city);
        }
        return null;
    }
}
