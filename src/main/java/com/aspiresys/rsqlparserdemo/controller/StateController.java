package com.aspiresys.rsqlparserdemo.controller;

import com.aspiresys.rsqlparserdemo.dto.CreateStateDTO;
import com.aspiresys.rsqlparserdemo.dto.StateDTO;
import com.aspiresys.rsqlparserdemo.entity.State;
import com.aspiresys.rsqlparserdemo.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/state")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping
    public List<StateDTO> getStates(@RequestParam(value = "query", required = false) String query) {
        return stateService.getStates(query);
    }

    @PostMapping
    public State save(@RequestBody CreateStateDTO createStateDTO) {
        return stateService.save(createStateDTO);
    }
}
