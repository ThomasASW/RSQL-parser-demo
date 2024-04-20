package com.aspiresys.rsqlparserdemo.services;

import com.aspiresys.rsqlparserdemo.dto.CityDTO;
import com.aspiresys.rsqlparserdemo.dto.CountryDTO;
import com.aspiresys.rsqlparserdemo.dto.StateDTO;
import com.aspiresys.rsqlparserdemo.entity.Country;
import com.aspiresys.rsqlparserdemo.entity.ZipCode;
import com.aspiresys.rsqlparserdemo.repository.CountryRepository;
import com.aspiresys.rsqlparserdemo.utils.CustomRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<CountryDTO> getCountries(String query) {
        Specification<Country> specification = null;
        if (query != null && !query.isBlank()) {
            Node root = new RSQLParser().parse(query);
            specification = root.accept(new CustomRsqlVisitor<>());
        }
        List<Country> countries = countryRepository.findAll(specification);
        return countries.stream().map(country -> {
            CountryDTO countryDTO = new CountryDTO();
            countryDTO.setName(country.getName());
            countryDTO.setStates(
                    country.getStates().stream().map(state -> {
                        StateDTO stateDTO = new StateDTO();
                        stateDTO.setName(state.getName());
                        stateDTO.setCities(
                                state.getCities().stream().map(city -> {
                                    CityDTO cityDTO = new CityDTO();
                                    cityDTO.setName(city.getName());
                                    cityDTO.setZipCodes(
                                            city.getZipCodes().stream().map(ZipCode::getZipCode).toList()
                                    );
                                    return cityDTO;
                                }).toList()
                        );
                        return stateDTO;
                    }).toList()
            );
            return countryDTO;
        }).toList();
    }

    public Country save(String name) {
        Country country = new Country();
        country.setName(name);
        return countryRepository.save(country);
    }
}
