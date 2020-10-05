package com.gharharyali.org.service.mapper;


import com.gharharyali.org.domain.*;
import com.gharharyali.org.service.dto.Quote1DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Quote1} and its DTO {@link Quote1DTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface Quote1Mapper extends EntityMapper<Quote1DTO, Quote1> {



    default Quote1 fromId(Long id) {
        if (id == null) {
            return null;
        }
        Quote1 quote1 = new Quote1();
        quote1.setId(id);
        return quote1;
    }
}
