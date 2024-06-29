package com.fath.comptapoc.model.mappers;

import com.fath.comptapoc.model.dtos.FinancialAccountDto;
import com.fath.comptapoc.model.entities.FinancialAccount;
import org.mapstruct.Mapper;

@Mapper
public interface IFinancialAccountMapper {

    FinancialAccount convertToFinancialAccount( FinancialAccountDto financialAccountDto );

    FinancialAccountDto convertToFinancialAccountDto( FinancialAccount financialAccount );
}
