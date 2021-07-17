package com.au.mergedcatalog.service;

import com.au.mergedcatalog.entities.ProductDto;

import java.util.List;

public interface CatalogService {
   List<ProductDto> buildFinalCatalog();
}
