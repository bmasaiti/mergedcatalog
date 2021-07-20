package com.au.mergedcatalog.mappers;

import com.au.mergedcatalog.entities.Product;
import com.au.mergedcatalog.entities.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.io.Serializable;

@Mapper( componentModel = "spring")
public interface MapStructMapper extends Serializable {

    ProductDto productToProductDto(Product product) ;
}
