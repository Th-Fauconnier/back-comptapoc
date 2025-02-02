package com.fath.comptapoc.controller;

import com.fath.comptapoc.model.dtos.OperationDto;
import com.fath.comptapoc.model.entities.Operation;
import com.fath.comptapoc.services.impl.OperationServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( path = "operation")
public class OperationController {

    @Autowired
    private OperationServiceImpl operationService;

    private final ModelMapper modelMapper;

    public OperationController() { this.modelMapper = new ModelMapper(); }

    @PostMapping("/create")
    public ResponseEntity<?> createOperation(@Valid @RequestBody OperationDto operationDto ){
        Operation operation = operationService.saveOperation( convertToEntity( operationDto ) );
        return new ResponseEntity<>(
                convertToDto( operation ),
                HttpStatus.CREATED
        );
    }

    private OperationDto convertToDto( Operation operation ){
        return modelMapper.map( operation, OperationDto.class );
    }

    private Operation convertToEntity( OperationDto operationDto ){
        return modelMapper.map( operationDto, Operation.class );
    }
}
