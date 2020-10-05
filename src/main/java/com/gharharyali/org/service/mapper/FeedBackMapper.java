package com.gharharyali.org.service.mapper;


import com.gharharyali.org.domain.*;
import com.gharharyali.org.service.dto.FeedBackDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FeedBack} and its DTO {@link FeedBackDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface FeedBackMapper extends EntityMapper<FeedBackDTO, FeedBack> {

    @Mapping(source = "product.id", target = "productId")
    FeedBackDTO toDto(FeedBack feedBack);

    @Mapping(source = "productId", target = "product")
    FeedBack toEntity(FeedBackDTO feedBackDTO);

    default FeedBack fromId(Long id) {
        if (id == null) {
            return null;
        }
        FeedBack feedBack = new FeedBack();
        feedBack.setId(id);
        return feedBack;
    }
}
