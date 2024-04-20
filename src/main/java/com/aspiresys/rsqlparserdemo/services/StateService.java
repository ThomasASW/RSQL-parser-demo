package com.aspiresys.rsqlparserdemo.services;

import com.aspiresys.rsqlparserdemo.dto.CityDTO;
import com.aspiresys.rsqlparserdemo.dto.CreateStateDTO;
import com.aspiresys.rsqlparserdemo.dto.StateDTO;
import com.aspiresys.rsqlparserdemo.entity.City;
import com.aspiresys.rsqlparserdemo.entity.Country;
import com.aspiresys.rsqlparserdemo.entity.State;
import com.aspiresys.rsqlparserdemo.entity.ZipCode;
import com.aspiresys.rsqlparserdemo.repository.CountryRepository;
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
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CountryRepository countryRepository;

    public List<StateDTO> getStates(String query) {
        Specification<State> specification = null;
        if (Objects.nonNull(query) && !query.isBlank()) {
            Node root = new RSQLParser().parse(query);
            specification = root.accept(new CustomRsqlVisitor<>());
        }
        List<State> states = stateRepository.findAll(specification);
        return states.stream().map(state -> {
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
        }).toList();
    }

    public State save(CreateStateDTO createStateDTO) {
        State state = new State();
        Optional<Country> country = countryRepository.findById(createStateDTO.getCountryId());
        if (country.isPresent()) {
            state.setName(createStateDTO.getName());
            state.setCountry(country.get());
            return stateRepository.save(state);
        }
        return null;
    }
}
