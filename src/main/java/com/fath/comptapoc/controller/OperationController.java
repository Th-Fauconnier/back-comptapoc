package com.fath.comptapoc.controller;

import com.fath.comptapoc.model.dtos.OperationDto;
import com.fath.comptapoc.model.entities.Operation;
import com.fath.comptapoc.services.impl.OperationServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( path = "operation")
public class OperationController {

    @Autowired
    private OperationServiceImpl operationService;

    private final ModelMapper modelMapper;

    public OperationController() { this.modelMapper = new ModelMapper(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOperationById(@PathVariable Long id) throws ClassNotFoundException {
        Operation operation = operationService.getOperationById( id );
        return new ResponseEntity<>(
                convertToDto( operation ),
                HttpStatus.OK
        );
    }

    private OperationDto convertToDto( Operation operation ){
        return modelMapper.map( operation, OperationDto.class );
    }

    private Operation convertToEntity( OperationDto operationDto ){
        return modelMapper.map( operationDto, Operation.class );
    }
}
