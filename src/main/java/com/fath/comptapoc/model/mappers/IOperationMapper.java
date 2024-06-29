package com.fath.comptapoc.model.mappers;

import com.fath.comptapoc.model.dtos.OperationDto;
import com.fath.comptapoc.model.entities.Operation;
import org.mapstruct.Mapper;

@Mapper
public interface IOperationMapper {

    Operation convertToOperation(OperationDto operationDto);

    OperationDto convertToOperationDto(Operation operation);
}
