package com.au.mergedcatalog.mappers;

import com.au.mergedcatalog.entities.Product;
import com.au.mergedcatalog.entities.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.io.Serializable;

@Mapper( componentModel = "spring")
public interface MapStructMapper extends Serializable {
//    @Mappings({
//            @Mapping(target="sku", source="product.sku"),
//            @Mapping(target="description", source="product.description"),
//            @Mapping(target="source", source="product.source")  })
    ProductDto productToProductDto(Product product) ;
}
