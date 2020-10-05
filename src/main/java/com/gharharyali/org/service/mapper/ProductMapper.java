package com.gharharyali.org.service.mapper;


import com.gharharyali.org.domain.*;
import com.gharharyali.org.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {NurseryMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "nursery.id", target = "nurseryId")
    ProductDTO toDto(Product product);

    @Mapping(target = "images", ignore = true)
    @Mapping(target = "removeImages", ignore = true)
    @Mapping(target = "ratings", ignore = true)
    @Mapping(target = "removeRating", ignore = true)
    @Mapping(source = "nurseryId", target = "nursery")
    Product toEntity(ProductDTO productDTO);

    default Product fromId(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
