package com.gharharyali.org.service.mapper;


import com.gharharyali.org.domain.*;
import com.gharharyali.org.service.dto.ImagesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Images} and its DTO {@link ImagesDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ImagesMapper extends EntityMapper<ImagesDTO, Images> {

    @Mapping(source = "product.id", target = "productId")
    ImagesDTO toDto(Images images);

    @Mapping(source = "productId", target = "product")
    Images toEntity(ImagesDTO imagesDTO);

    default Images fromId(Long id) {
        if (id == null) {
            return null;
        }
        Images images = new Images();
        images.setId(id);
        return images;
    }
}
