package com.fath.comptapoc.services.impl;

import com.fath.comptapoc.model.entities.AccountingEntry;
import com.fath.comptapoc.model.entities.Operation;
import com.fath.comptapoc.repository.OperationRepository;
import com.fath.comptapoc.services.contracts.IOperationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class OperationServiceImpl implements IOperationService {

    @Autowired
    private Validator validator;

    @Autowired
    private OperationRepository operationRepository;

    @Override
    public Operation saveOperation(Operation operation) throws ConstraintViolationException {
        isOperationValid( operation );
        return operationRepository.save( operation );
    }

    /**
     *
     * @param operation as bean to check
     * @throws ConstraintViolationException
     * if the operation isn't a valid bean, a ConstraintViolation will be thrown
     * constraints are define in the operation bean
     */
    private void isOperationValid( Operation operation ){
        Set<ConstraintViolation<Operation>> violations = validator.validate( operation );

        if( !violations.isEmpty() ){
            StringBuilder sb = new StringBuilder();
            violations.forEach( v -> sb.append( v.getMessage() ).append(". "));
            throw new ConstraintViolationException( "Error occurred: " + sb.toString(), violations );
        }
    }
}
