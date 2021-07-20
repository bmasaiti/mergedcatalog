package com.au.mergedcatalog.service;

import com.au.mergedcatalog.entities.Product;
import com.au.mergedcatalog.entities.ProductDto;
import com.au.mergedcatalog.fileprocessor.CatalogFileProcessor;
import com.au.mergedcatalog.repository.CombinedCatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CatalogServiceUtils {

     private final CombinedCatalogRepository combinedCatalogRepository;
     private final CatalogFileProcessor catalogFileProcessor;

    public void writeCatalogToCsvFile(String outputFilePath, List<ProductDto> productDtoList) {
        catalogFileProcessor.writeCombinedCatalogToCsVFile(productDtoList, outputFilePath);
        log.info(productDtoList.toString());
    }

    public void writeFinalCatalogToDb(List<Product> finalProductCatalog) {
        combinedCatalogRepository.saveAll(finalProductCatalog);
    }

}
