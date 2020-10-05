package com.gharharyali.org.service.mapper;


import com.gharharyali.org.domain.*;
import com.gharharyali.org.service.dto.TransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {NurseryMapper.class})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    @Mapping(source = "nursery.id", target = "nurseryId")
    TransactionDTO toDto(Transaction transaction);

    @Mapping(source = "nurseryId", target = "nursery")
    Transaction toEntity(TransactionDTO transactionDTO);

    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
