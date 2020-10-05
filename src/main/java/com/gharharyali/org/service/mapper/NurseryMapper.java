package com.gharharyali.org.service.mapper;


import com.gharharyali.org.domain.*;
import com.gharharyali.org.service.dto.NurseryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Nursery} and its DTO {@link NurseryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NurseryMapper extends EntityMapper<NurseryDTO, Nursery> {


    @Mapping(target = "products", ignore = true)
    @Mapping(target = "removeProduct", ignore = true)
    @Mapping(target = "transactions", ignore = true)
    @Mapping(target = "removeTransactions", ignore = true)
    Nursery toEntity(NurseryDTO nurseryDTO);

    default Nursery fromId(Long id) {
        if (id == null) {
            return null;
        }
        Nursery nursery = new Nursery();
        nursery.setId(id);
        return nursery;
    }
}
