package com.fath.comptapoc.services.contracts;

import com.fath.comptapoc.model.entities.Operation;
import jakarta.validation.ConstraintViolationException;

public interface IOperationService {
    Operation saveOperation(Operation operation) throws ConstraintViolationException;

    /**
     * Retrieve an operation by his id in the database
     * @param id as Long
     * @return if it's existing in database, the operation linked by the id
     * @throws ClassNotFoundException if none is found with given id
     */
    Operation getOperationById(Long id) throws ClassNotFoundException;
}
