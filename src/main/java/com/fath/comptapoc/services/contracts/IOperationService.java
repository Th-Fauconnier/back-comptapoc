package com.fath.comptapoc.services.contracts;

import com.fath.comptapoc.model.entities.Operation;
import jakarta.validation.ConstraintViolationException;

public interface IOperationService {
    Operation saveOperation(Operation operation) throws ConstraintViolationException;
}
